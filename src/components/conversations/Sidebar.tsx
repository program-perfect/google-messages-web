"use client";

import { useChatStore } from "@/store/useChatStore";
import { ConversationList } from "./ConversationList";
import { SearchOverlay } from "@/components/search/SearchOverlay";
import { AnimatePresence, motion } from "framer-motion";

const tabs = [
  { key: "messages", label: "All", icon: "chat_bubble" },
  { key: "pinned", label: "Pinned", icon: "push_pin" },
  { key: "archived", label: "Archived", icon: "archive" },
] as const;

export function Sidebar() {
  const searchOpen = useChatStore((s) => s.searchOpen);
  const theme = useChatStore((s) => s.theme);
  const isOffline = useChatStore((s) => s.isOffline);
  const activeTab = useChatStore((s) => s.activeTab);

  const isDark = theme === "dark";

  return (
    <div className="flex h-full flex-col overflow-hidden">
      <header className="shrink-0 px-4 pb-3 pt-4">
        <div className="mb-4 flex items-center gap-3">
          <div className="gm-brand-orb h-12 w-12 shrink-0 rounded-[20px]" aria-hidden="true" />

          <div className="min-w-0 flex-1">
            <p
              className="leading-none"
              style={{
                color: "var(--md-sys-color-on-surface-variant)",
                fontSize: 12,
                fontWeight: 700,
                letterSpacing: "0.08em",
                textTransform: "uppercase",
              }}
            >
              Material 3 Expressive
            </p>
            <h1
              className="truncate"
              style={{
                color: "var(--md-sys-color-on-surface)",
                fontSize: 30,
                fontWeight: 800,
                letterSpacing: "-0.04em",
              }}
            >
              Messages
            </h1>
          </div>

          <md-icon-button
            aria-label="Toggle theme"
            onClick={() => useChatStore.getState().setTheme(isDark ? "light" : "dark")}
          >
            <md-icon>{isDark ? "light_mode" : "dark_mode"}</md-icon>
          </md-icon-button>

          <md-icon-button aria-label="More options">
            <md-icon>more_vert</md-icon>
          </md-icon-button>
        </div>

        <div className="gm-panel p-3">
          <md-filled-text-field
            className="gm-search-field"
            label="Search messages"
            type="search"
            onFocus={() => useChatStore.getState().setSearchOpen(true)}
            onClick={() => useChatStore.getState().setSearchOpen(true)}
          >
            <md-icon slot="leading-icon">search</md-icon>
            <md-icon slot="trailing-icon">tune</md-icon>
          </md-filled-text-field>
        </div>

        <md-chip-set className="gm-chip-row mt-3" aria-label="Conversation filters">
          {tabs.map((tab) => (
            <md-filter-chip
              key={tab.key}
              label={tab.label}
              selected={activeTab === tab.key}
              onClick={() => useChatStore.getState().setActiveTab(tab.key)}
            >
              <md-icon slot="icon">{tab.icon}</md-icon>
            </md-filter-chip>
          ))}
        </md-chip-set>
      </header>

      <AnimatePresence>
        {isOffline && (
          <motion.div
            initial={{ height: 0, opacity: 0 }}
            animate={{ height: "auto", opacity: 1 }}
            exit={{ height: 0, opacity: 0 }}
            className="overflow-hidden px-4"
          >
            <div
              className="mb-3 flex items-center gap-2 rounded-[24px] px-4 py-3"
              style={{
                background: "var(--md-sys-color-error-container)",
                color: "var(--md-sys-color-on-error-container)",
              }}
            >
              <md-icon>wifi_off</md-icon>
              <span style={{ fontSize: 13, fontWeight: 600 }}>No internet connection</span>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      <div className="relative flex min-h-0 flex-1 flex-col overflow-hidden">
        <ConversationList />

        <div className="pointer-events-none absolute bottom-5 right-5 z-10">
          <md-fab
            className="pointer-events-auto"
            variant="primary"
            label="Start chat"
            aria-label="Start new chat"
          >
            <md-icon slot="icon">edit</md-icon>
          </md-fab>
        </div>
      </div>

      <AnimatePresence>
        {searchOpen && (
          <SearchOverlay key="search" onClose={() => useChatStore.getState().setSearchOpen(false)} />
        )}
      </AnimatePresence>
    </div>
  );
}
