"use client";

import { useState, useCallback } from "react";
import { AnimatePresence, motion } from "framer-motion";
import { useShallow } from "zustand/shallow";
import { useQuery } from "@tanstack/react-query";
import { useChatStore } from "@/store/useChatStore";
import { Avatar } from "@/components/ui/Avatar";
import { MessageList } from "./MessageList";
import { MessageComposer } from "./MessageComposer";
import { ContextMenu } from "./ContextMenu";
import { ReactionPicker } from "./ReactionPicker";
import { fetchConversations } from "@/services/mockApi";
import { idbGetAllConversations } from "@/lib/idb";
import type { Message } from "@/types/global";

const EMPTY_MESSAGES: Message[] = [];

interface ChatWindowProps {
  conversationId: string;
}

export function ChatWindow({ conversationId }: ChatWindowProps) {
  const { data: conversations = [] } = useQuery({
    queryKey: ["conversations"],
    queryFn: async () => {
      try {
        return await fetchConversations();
      } catch {
        return idbGetAllConversations();
      }
    },
    staleTime: Infinity,
  });
  const conversation = conversations.find((c) => c.id === conversationId) ?? null;

  const messages = useChatStore(useShallow((s) => s.messages[conversationId] ?? EMPTY_MESSAGES));

  const [contextMenuState, setContextMenuState] = useState<{
    messageId: string;
    position: { x: number; y: number };
  } | null>(null);

  const [reactionPicker, setReactionPicker] = useState<{
    messageId: string;
    position: { x: number; y: number };
  } | null>(null);

  const contextMessage: Message | undefined = contextMenuState
    ? messages.find((m) => m.id === contextMenuState.messageId)
    : undefined;

  const handleBack = useCallback(() => {
    const { setActiveConversationId, setSidebarOpen } = useChatStore.getState();
    setActiveConversationId(null);
    setSidebarOpen(true);
  }, []);

  const handleContextMenu = useCallback((messageId: string, e: React.MouseEvent) => {
    e.preventDefault();
    setReactionPicker(null);
    setContextMenuState({ messageId, position: { x: e.clientX, y: e.clientY } });
  }, []);

  const handleReactionClick = useCallback((messageId: string, e?: React.MouseEvent) => {
    setContextMenuState(null);
    const x = e ? e.clientX : window.innerWidth / 2;
    const y = e ? Math.max(e.clientY - 56, 80) : 120;
    setReactionPicker({ messageId, position: { x, y } });
  }, []);

  return (
    <motion.div
      className="gm-chat-window flex h-full flex-col overflow-hidden md:rounded-[36px]"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.18 }}
    >
      <header className="gm-chat-appbar flex shrink-0 items-center gap-2 px-3">
        <md-icon-button className="md:hidden" onClick={handleBack} aria-label="Back">
          <md-icon>arrow_back</md-icon>
        </md-icon-button>

        <div className="flex min-w-0 flex-1 items-center gap-3">
          {conversation && (
            <Avatar
              name={conversation.name ?? ""}
              initials={conversation.initials ?? ""}
              avatarColor={conversation.avatarColor}
              avatar={conversation.avatar}
              size={46}
              isGroup={conversation.isGroup}
            />
          )}
          <div className="min-w-0 flex-1">
            <div className="flex items-center gap-2">
              <h2
                className="truncate"
                style={{
                  fontSize: 18,
                  fontWeight: 800,
                  letterSpacing: "-0.02em",
                  color: "var(--md-sys-color-on-surface)",
                }}
              >
                {conversation?.name ?? "Loading..."}
              </h2>
              {conversation?.pinned && <md-icon style={{ fontSize: 17 }}>push_pin</md-icon>}
            </div>
            <p
              className="truncate"
              style={{ color: "var(--md-sys-color-on-surface-variant)", fontSize: 12, fontWeight: 600 }}
            >
              {conversation?.phone ?? "Material 3 Web conversation"}
            </p>
          </div>
        </div>

        <div className="flex shrink-0 items-center gap-1">
          <md-icon-button aria-label="Voice call">
            <md-icon>call</md-icon>
          </md-icon-button>
          <md-icon-button aria-label="Video call">
            <md-icon>videocam</md-icon>
          </md-icon-button>
          <md-icon-button aria-label="More options">
            <md-icon>more_vert</md-icon>
          </md-icon-button>
        </div>
      </header>

      <MessageList
        conversationId={conversationId}
        onContextMenu={handleContextMenu}
        onReactionClick={handleReactionClick}
      />

      <MessageComposer conversationId={conversationId} />

      <AnimatePresence>
        {contextMenuState && contextMessage && (
          <ContextMenu
            key="context-menu"
            message={contextMessage}
            position={contextMenuState.position}
            onClose={() => setContextMenuState(null)}
            onReact={(id) => {
              setContextMenuState(null);
              setReactionPicker({ messageId: id, position: contextMenuState.position });
            }}
            onReply={(id) => {
              useChatStore.getState().setReplyingTo(conversationId, id);
              setContextMenuState(null);
            }}
          />
        )}
      </AnimatePresence>

      <AnimatePresence>
        {reactionPicker && (
          <ReactionPicker
            key="reaction-picker"
            messageId={reactionPicker.messageId}
            conversationId={conversationId}
            position={reactionPicker.position}
            onClose={() => setReactionPicker(null)}
          />
        )}
      </AnimatePresence>
    </motion.div>
  );
}
