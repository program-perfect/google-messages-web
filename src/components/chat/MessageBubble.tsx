"use client";

import { motion } from "framer-motion";
import type { Message } from "@/types/global";

interface MessageBubbleProps {
  message: Message;
  isGrouped: boolean;
  isLastInGroup: boolean;
  onContextMenu: (messageId: string, e: React.MouseEvent) => void;
  onReactionClick: (messageId: string, e?: React.MouseEvent) => void;
  isOptimistic?: boolean;
  replyToMessage?: Message | null;
}

const STATUS_ICONS: Record<string, string> = {
  sent: "check",
  delivered: "done",
  read: "done_all",
};

function formatMessageTime(iso: string): string {
  return new Intl.DateTimeFormat("en", {
    hour: "numeric",
    minute: "2-digit",
  }).format(new Date(iso));
}

export function MessageBubble({
  message,
  isGrouped,
  isLastInGroup,
  onContextMenu,
  onReactionClick,
  isOptimistic = false,
  replyToMessage,
}: MessageBubbleProps) {
  const isOutgoing = message.direction === "outgoing";

  if (message.isDeleted) {
    return (
      <div className={`flex ${isOutgoing ? "justify-end" : "justify-start"} px-4 py-1`}>
        <div
          className="flex items-center gap-2 rounded-full px-4 py-2"
          style={{
            border: "1px solid var(--md-sys-color-outline-variant)",
            color: "var(--md-sys-color-on-surface-variant)",
            fontSize: 13,
            fontStyle: "italic",
          }}
        >
          <md-icon style={{ fontSize: 16 }}>block</md-icon>
          Message deleted
        </div>
      </div>
    );
  }

  const outgoingRadius = isLastInGroup ? "28px 28px 8px 28px" : "28px";
  const incomingRadius = isLastInGroup ? "28px 28px 28px 8px" : "28px";
  const borderRadius = isGrouped ? "28px" : isOutgoing ? outgoingRadius : incomingRadius;

  return (
    <motion.div
      initial={{ opacity: 0, y: 10, scale: 0.96 }}
      animate={{ opacity: isOptimistic ? 0.72 : 1, y: 0, scale: 1 }}
      transition={{ type: "spring", stiffness: 460, damping: 34 }}
      className={`flex flex-col ${isOutgoing ? "items-end" : "items-start"} px-4 ${
        isGrouped ? "py-0.5" : "py-1"
      }`}
    >
      <div
        className="group relative max-w-[78%] cursor-pointer lg:max-w-[66%]"
        onContextMenu={(e) => {
          e.preventDefault();
          onContextMenu(message.id, e);
        }}
      >
        <div
          className={`gm-bubble ${isOutgoing ? "gm-bubble-out" : "gm-bubble-in"} break-words text-sm leading-relaxed`}
          style={{
            borderRadius,
            overflow: "hidden",
            overflowWrap: "break-word",
            wordBreak: "break-word",
          }}
        >
          {replyToMessage && !replyToMessage.isDeleted && (
            <div
              className="mx-2 mt-2 flex items-stretch gap-2 rounded-[20px] px-3 py-2"
              style={{
                background: isOutgoing
                  ? "rgba(255,255,255,0.16)"
                  : "color-mix(in srgb, var(--md-sys-color-primary-container) 55%, transparent)",
              }}
            >
              <div
                className="w-1 shrink-0 rounded-full"
                style={{
                  background: isOutgoing
                    ? "rgba(255,255,255,0.74)"
                    : "var(--gm-expressive-coral)",
                }}
              />

              <div className="min-w-0 flex-1">
                <p
                  className="truncate"
                  style={{
                    color: isOutgoing ? "rgba(255,255,255,0.86)" : "var(--md-sys-color-primary)",
                    fontSize: 11,
                    fontWeight: 800,
                  }}
                >
                  {replyToMessage.direction === "outgoing" ? "You" : "Them"}
                </p>
                <p
                  className="truncate"
                  style={{
                    color: isOutgoing
                      ? "rgba(255,255,255,0.78)"
                      : "var(--md-sys-color-on-surface-variant)",
                    fontSize: 12,
                  }}
                >
                  {replyToMessage.text}
                </p>
              </div>
            </div>
          )}

          <div className="px-4 py-2.5">{message.text}</div>
        </div>

        <md-icon-button
          className="absolute opacity-0 transition-opacity group-hover:opacity-100"
          style={{
            [isOutgoing ? "left" : "right"]: -42,
            background: "var(--md-sys-color-surface-container-high)",
            borderRadius: "50%",
            boxShadow: "var(--gm-shadow-1)",
            top: "50%",
            transform: "translateY(-50%)",
          }}
          onClick={(e) => onReactionClick(message.id, e)}
          aria-label="Add reaction"
        >
          <md-icon>add_reaction</md-icon>
        </md-icon-button>
      </div>

      {message.reactions.length > 0 && (
        <div className="mt-1 flex flex-wrap gap-1">
          {message.reactions.map((reaction) => (
            <md-assist-chip
              key={reaction.emoji}
              className="gm-reaction-chip"
              label={`${reaction.emoji}${reaction.count > 1 ? ` ${reaction.count}` : ""}`}
              onClick={(e) => onReactionClick(message.id, e)}
              aria-label={`${reaction.emoji} reaction, ${reaction.count}`}
            />
          ))}
        </div>
      )}

      {isLastInGroup && (
        <div
          className="mt-1 flex items-center gap-1 px-1"
          style={{
            color: "var(--md-sys-color-on-surface-variant)",
            fontSize: 11,
            fontWeight: 600,
          }}
        >
          <span>{formatMessageTime(message.timestamp)}</span>
          {isOutgoing && (
            <md-icon
              style={{
                color:
                  message.status === "read"
                    ? "var(--gm-expressive-coral)"
                    : "var(--md-sys-color-on-surface-variant)",
                fontSize: 14,
              }}
              aria-label={message.status}
            >
              {STATUS_ICONS[message.status] ?? "check"}
            </md-icon>
          )}
        </div>
      )}
    </motion.div>
  );
}
