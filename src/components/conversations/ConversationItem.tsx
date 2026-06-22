"use client";

type RowData = {
  id: string;
  name: string;
  lastMessage: string;
};

interface Props {
  conversation: RowData;
  isActive: boolean;
  onSelect: (id: string) => void;
  onContextMenu?: unknown;
}

export function ConversationItem(props: Props) {
  const row = props.conversation;

  return (
    <button
      className={"gm-conversation-item w-full p-4 text-left " + (props.isActive ? "active" : "")}
      onClick={() => props.onSelect(row.id)}
    >
      <div>{row.name}</div>
      <div>{row.lastMessage}</div>
    </button>
  );
}
