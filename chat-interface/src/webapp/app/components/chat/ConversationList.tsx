import { ConversationCard } from "./ConversationCard";
import type { Conversation } from "./types";

type ConversationListProps = {
  conversations: Conversation[];
  selectedId: string;
  onSelect: (id: string) => void;
};

export function ConversationList({
  conversations,
  selectedId,
  onSelect,
}: ConversationListProps) {
  return (
    <div className="flex h-full max-h-[calc(100vh-280px)] flex-col gap-4 pb-2">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-semibold text-[#191510]">
          All conversations
        </h2>
        <span className="rounded-full bg-[#eaded0] px-2.5 py-0.5 font-['IBM_Plex_Mono'] text-[11px] text-[#3b2f26]">
          {conversations.length}
        </span>
      </div>
      <div className="flex flex-1 flex-col gap-3 overflow-y-auto pt-1 pr-1">
        {conversations.map((conversation) => (
          <ConversationCard
            key={conversation.id}
            conversation={conversation}
            isActive={conversation.id === selectedId}
            onSelect={onSelect}
          />
        ))}
      </div>
    </div>
  );
}
