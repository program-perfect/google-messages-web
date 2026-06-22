"use client";

import { useEffect } from "react";
import { AnimatePresence, motion } from "framer-motion";
import { useChatStore } from "@/store/useChatStore";
import { NavigationRail } from "@/components/navigation/NavigationRail";
import { BottomNavBar } from "@/components/navigation/BottomNavBar";
import { Sidebar } from "@/components/conversations/Sidebar";
import { ChatWindow } from "@/components/chat/ChatWindow";
import { EmptyState } from "@/components/chat/EmptyState";

export function AppShell() {
  const activeConversationId = useChatStore((s) => s.activeConversationId);
  const sidebarOpen = useChatStore((s) => s.sidebarOpen);

  useEffect(() => {
    const setSearchOpen = useChatStore.getState().setSearchOpen;
    const setActiveConversationId = useChatStore.getState().setActiveConversationId;

    function onKeyDown(e: KeyboardEvent) {
      const target = e.target as HTMLElement;
      const isInput =
        target.tagName === "INPUT" ||
        target.tagName === "TEXTAREA" ||
        target.tagName.includes("MD-") ||
        target.isContentEditable;

      if ((e.ctrlKey || e.metaKey) && e.key === "f") {
        e.preventDefault();
        setSearchOpen(true);
        return;
      }

      if (e.key === "Escape" && !isInput) {
        const state = useChatStore.getState();
        if (state.searchOpen) {
          setSearchOpen(false);
        } else if (state.activeConversationId && window.innerWidth < 768) {
          setActiveConversationId(null);
          useChatStore.getState().setSidebarOpen(true);
        }
      }
    }

    document.addEventListener("keydown", onKeyDown);
    return () => document.removeEventListener("keydown", onKeyDown);
  }, []);

  useEffect(() => {
    function onResize() {
      if (window.innerWidth >= 768) {
        useChatStore.getState().setSidebarOpen(true);
      }
    }
    window.addEventListener("resize", onResize);
    return () => window.removeEventListener("resize", onResize);
  }, []);

  return (
    <div className="gm-shell flex h-screen overflow-hidden font-sans">
      <NavigationRail />

      <div className="flex min-w-0 flex-1 flex-col overflow-hidden p-0 md:p-3 md:pl-0">
        <div className="relative flex flex-1 overflow-hidden md:gap-3">
          <AnimatePresence initial={false}>
            {(sidebarOpen || typeof window === "undefined") && (
              <motion.aside
                key="sidebar"
                className="gm-sidebar-shell flex flex-col overflow-hidden md:rounded-[36px]"
                style={{
                  width: "100%",
                  maxWidth: 432,
                  minWidth: 292,
                }}
                initial={{ x: -24, opacity: 0, scale: 0.98 }}
                animate={{ x: 0, opacity: 1, scale: 1 }}
                exit={{ x: -24, opacity: 0, scale: 0.98 }}
                transition={{ type: "spring", stiffness: 360, damping: 36 }}
              >
                <Sidebar />
              </motion.aside>
            )}
          </AnimatePresence>

          <main className="flex min-w-0 flex-1 flex-col overflow-hidden md:rounded-[36px]">
            <AnimatePresence mode="wait">
              {activeConversationId ? (
                <motion.div
                  key={activeConversationId}
                  className="flex flex-1 flex-col overflow-hidden"
                  initial={{ opacity: 0, x: 16, scale: 0.992 }}
                  animate={{ opacity: 1, x: 0, scale: 1 }}
                  exit={{ opacity: 0, x: 16, scale: 0.992 }}
                  transition={{ duration: 0.24, ease: [0.2, 0, 0, 1] }}
                  style={{ height: "100%" }}
                >
                  <ChatWindow conversationId={activeConversationId} />
                </motion.div>
              ) : (
                <motion.div
                  key="empty"
                  className="flex flex-1 flex-col overflow-hidden"
                  initial={{ opacity: 0, scale: 0.98 }}
                  animate={{ opacity: 1, scale: 1 }}
                  exit={{ opacity: 0, scale: 0.98 }}
                  transition={{ duration: 0.22 }}
                  style={{ height: "100%" }}
                >
                  <div
                    className={`h-full ${
                      !activeConversationId && sidebarOpen ? "hidden md:flex" : "flex"
                    } flex-col`}
                  >
                    <EmptyState />
                  </div>
                </motion.div>
              )}
            </AnimatePresence>
          </main>
        </div>

        <BottomNavBar />
      </div>
    </div>
  );
}
