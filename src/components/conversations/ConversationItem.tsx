"use client";

type Item = { id: string; name: string; lastMessage: string };

interface Props {
  item: Item;
  active: boolean;
  onOpen: (id: string) => void;
}

export function ConversationItem({ item, active, onOpen }: Props) {
  return (
    <button
      className={"gm-conversation-item w-full p-4 text-left " + (active ? "active" : "")}
      onClick={() => onOpen(item.id)}
    >
      <div>{item.name}</div>
      <div>{item.lastMessage}</div>
    </button>
  );
}
