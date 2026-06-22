"use client";

import { useEffect } from "react";
import { useChatStore } from "@/store/useChatStore";

/** Listens to browser online/offline events and syncs the store. */
export default function OfflineWatcher() {
  useEffect(() => {
    const { setIsOffline } = useChatStore.getState();
    const onOnline = () => setIsOffline(false);
    const onOffline = () => setIsOffline(true);

    setIsOffline(!navigator.onLine);

    window.addEventListener("online", onOnline);
    window.addEventListener("offline", onOffline);
    return () => {
      window.removeEventListener("online", onOnline);
      window.removeEventListener("offline", onOffline);
    };
  }, []);

  return null;
}
