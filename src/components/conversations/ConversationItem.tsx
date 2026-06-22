"use client";

import { Avatar } from "@/components/ui/Avatar";
import { formatConversationTime } from "@/lib/dateUtils";
import type { Conversation } from "@/types/global";

interface ConversationItemProps {
  conversation: Conversation;
  isActive: boolean;
  onSelect: (id: string) => void;
  onContextMenu?: (id: string, e: React.MouseEvent) => void;
}

export function ConversationItem({
  conversation,
  isActive,
  onSelect,
  onContextMenu,
}: ConversationItemProps) {
  const {
    id,
    name,
    initials,
    avatar,
    avatarColor,
    lastMessage,
    updatedAt,
    pinned,
    unreadCount,
    isGroup,
  } = conversation;

  const hasUnread = unreadCount > 0;

  return (
    <md-list-item
      type="button"
      interactive
      className={`gm-conversation-item ${isActive ? "active" : ""}`}
      onClick={() => onSelect(id)}
      onContextMenu={(e) => {
        e.preventDefault();
        onContextMenu?.(id, e);
      }}
      aria-label={`Conversation with ${name}${hasUnread ? `, ${unreadCount} unread` : ""}`}
      aria-current={isActive ? "true" : undefined}
    >
      <div slot="start" className="gm-list-avatar">
        <Avatar
          name={name}
          initials={initials}
          avatarColor={avatarColor}
          avatar={avatar}
          size={52}
          isGroup={isGroup}
        />
      </div>

      <div slot="headline" className="flex min-w-0 items-center gap-2">
        <span
          className="truncate"
          style={{
            color: isActive
              ? "var(--md-sys-color-on-secondary-container)"
              : "var(--md-sys-color-on-surface)",
            fontSize: 16,
            fontWeight: hasUnread ? 800 : 650,
          }}
        >
          {name}
        </span>

        {pinned && (
          <md-icon
            style={{
              color: "var(--md-sys-color-primary)",
              fontSize: 16,
              fontVariationSettings: "'FILL' 1",
            }}
            aria-label="Pinned"
          >
            push_pin
          </md-icon>
        )}
      </div>

      <div slot="supporting-text" className="flex min-w-0 items-center gap-2">
        <span
          className="truncate"
          style={{
            color: isActive
              ? "var(--md-sys-color-on-secondary-container)"
              : hasUnread
                ? "var(--md-sys-color-on-surface)"
                : "var(--md-sys-color-on-surface-variant)",
            fontSize: 13,
            fontWeight: hasUnread ? 650 : 450,
            opacity: isActive ? 0.88 : 1,
          }}
        >
          {lastMessage}
        </span>
      </div>

      <div slot="trailing-supporting-text" className="flex flex-col items-end gap-2">
        <span
          style={{
            color: hasUnread
              ? "var(--gm-expressive-coral)"
              : "var(--md-sys-color-on-surface-variant)",
            fontSize: 12,
            fontWeight: hasUnread ? 800 : 600,
          }}
        >
          {formatConversationTime(updatedAt)}
        </span>

        {hasUnread && (
          <span className="gm-unread-badge" aria-label={`${unreadCount} unread messages`}>
            {unreadCount > 99 ? "99+" : unreadCount}
          </span>
        )}
      </div>
    </md-list-item>
  );
}
