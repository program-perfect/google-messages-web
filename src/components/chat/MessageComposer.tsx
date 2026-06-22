"use client";

import { useCallback, useRef, useState } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { AnimatePresence, motion } from "framer-motion";
import { useShallow } from "zustand/shallow";
import { useChatStore } from "@/store/useChatStore";
import { idbPutMessages } from "@/lib/idb";
import { nanoid } from "@/lib/nanoid";
import { sendMessage, simulateIncomingMessage } from "@/services/mockApi";
import type { Message } from "@/types/global";

const EMPTY_MESSAGES: Message[] = [];

interface MessageComposerProps {
  conversationId: string;
}

export function MessageComposer({ conversationId }: MessageComposerProps) {
  const [text, setText] = useState("");
  const [sending, setSending] = useState(false);
  const [attachmentsOpen, setAttachmentsOpen] = useState(false);
  const queryClient = useQueryClient();
  const typingTimerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const appendMessage = useChatStore.getState().appendMessage;
  const setTyping = useChatStore.getState().setTyping;
  const setReplyingTo = useChatStore.getState().setReplyingTo;
  const replyingToId = useChatStore((s) => s.replyingTo[conversationId] ?? null);
  const messages = useChatStore(useShallow((s) => s.messages[conversationId] ?? EMPTY_MESSAGES));

  const replyingToMessage: Message | null = replyingToId
    ? (messages.find((m) => m.id === replyingToId) ?? null)
    : null;

  const handleInput = useCallback(
    (e: React.FormEvent<HTMLElement>) => {
      const nextValue = (e.currentTarget as HTMLInputElement & { value: string }).value;
      setText(nextValue);

      if (nextValue.trim()) {
        setTyping(conversationId, true);
        if (typingTimerRef.current) clearTimeout(typingTimerRef.current);
        typingTimerRef.current = setTimeout(() => setTyping(conversationId, false), 2000);
      } else {
        setTyping(conversationId, false);
        if (typingTimerRef.current) clearTimeout(typingTimerRef.current);
      }
    },
    [conversationId, setTyping]
  );

  async function send() {
    const trimmed = text.trim();
    if (!trimmed || sending) return;

    const currentReplyTo = replyingToId;

    setSending(true);
    setText("");
    setTyping(conversationId, false);
    setReplyingTo(conversationId, null);

    const optimisticMsg: Message = {
      id: `opt-${nanoid()}`,
      conversationId,
      text: trimmed,
      timestamp: new Date().toISOString(),
      direction: "outgoing",
      status: "sent",
      reactions: [],
      isDeleted: false,
      replyTo: currentReplyTo,
    };

    appendMessage(optimisticMsg);

    try {
      const confirmedMsg = await sendMessage(conversationId, trimmed, currentReplyTo);
      appendMessage(confirmedMsg);
      await idbPutMessages([confirmedMsg]);
      queryClient.invalidateQueries({ queryKey: ["conversations"] });

      setTimeout(() => {
        simulateIncomingMessage(
          conversationId,
          (incomingMsg) => {
            appendMessage(incomingMsg);
            idbPutMessages([incomingMsg]);
            queryClient.invalidateQueries({ queryKey: ["conversations"] });
          },
          (typing) => setTyping(conversationId, typing)
        );
      }, 800 + Math.random() * 1200);
    } finally {
      setSending(false);
    }
  }

  function handleKeyDown(e: React.KeyboardEvent<HTMLElement>) {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      send();
    }

    if (e.key === "Escape" && replyingToId) {
      setReplyingTo(conversationId, null);
    }
  }

  const canSend = text.trim().length > 0 && !sending;

  return (
    <div className="gm-composer-shell shrink-0 px-3 py-3">
      <AnimatePresence>
        {replyingToMessage && (
          <motion.div
            key="reply-bar"
            initial={{ height: 0, opacity: 0, y: 8 }}
            animate={{ height: "auto", opacity: 1, y: 0 }}
            exit={{ height: 0, opacity: 0, y: 8 }}
            transition={{ duration: 0.18 }}
            className="overflow-hidden"
          >
            <div className="gm-panel mb-2 flex items-center gap-3 px-4 py-3">
              <div
                className="w-1 self-stretch rounded-full"
                style={{ background: "var(--gm-expressive-coral)", minHeight: 28 }}
              />

              <div className="min-w-0 flex-1">
                <p
                  style={{
                    color: "var(--md-sys-color-primary)",
                    fontSize: 12,
                    fontWeight: 800,
                  }}
                >
                  Replying to {replyingToMessage.direction === "outgoing" ? "you" : "them"}
                </p>
                <p
                  className="truncate"
                  style={{ color: "var(--md-sys-color-on-surface-variant)", fontSize: 13 }}
                >
                  {replyingToMessage.text}
                </p>
              </div>

              <md-icon-button
                aria-label="Cancel reply"
                onClick={() => setReplyingTo(conversationId, null)}
              >
                <md-icon>close</md-icon>
              </md-icon-button>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      <div className="gm-composer-card flex items-end gap-2 p-2">
        <md-icon-button
          id="attach-button"
          aria-label="Attach"
          onClick={() => setAttachmentsOpen((value) => !value)}
        >
          <md-icon>add</md-icon>
        </md-icon-button>

        <md-menu
          open={attachmentsOpen}
          anchor="attach-button"
          quick
          onClosed={() => setAttachmentsOpen(false)}
        >
          <md-menu-item>
            <md-icon slot="start">image</md-icon>
            <div slot="headline">Photo</div>
          </md-menu-item>
          <md-menu-item>
            <md-icon slot="start">photo_camera</md-icon>
            <div slot="headline">Camera</div>
          </md-menu-item>
          <md-menu-item>
            <md-icon slot="start">description</md-icon>
            <div slot="headline">Document</div>
          </md-menu-item>
          <md-menu-item>
            <md-icon slot="start">person</md-icon>
            <div slot="headline">Contact</div>
          </md-menu-item>
        </md-menu>

        <md-filled-text-field
          className="gm-composer-field"
          label="Message"
          value={text}
          type="textarea"
          rows={1}
          onInput={handleInput}
          onKeyDown={handleKeyDown}
        >
          <md-icon slot="trailing-icon">sentiment_satisfied</md-icon>
        </md-filled-text-field>

        <motion.div
          animate={{ rotate: canSend ? 0 : -8, scale: canSend ? 1 : 0.96 }}
          transition={{ type: "spring", stiffness: 500, damping: 24 }}
        >
          <md-fab
            variant="primary"
            size="small"
            aria-label={canSend ? "Send message" : "Voice message"}
            onClick={send}
            lowered={!canSend}
          >
            <md-icon slot="icon">{canSend ? "send" : "mic"}</md-icon>
          </md-fab>
        </motion.div>
      </div>
    </div>
  );
}
