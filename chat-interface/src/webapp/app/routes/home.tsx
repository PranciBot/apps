import { useMemo, useState } from "react";
import { ChatHeader } from "../components/chat/ChatHeader";
import { ConversationList } from "../components/chat/ConversationList";
import { ConversationPanel } from "../components/chat/ConversationPanel";
import { EmptyState } from "../components/chat/EmptyState";
import { seedConversations } from "../components/chat/data";
import type { Conversation } from "../components/chat/types";
import type { Route } from "./+types/home";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Wweb Chat" },
    {
      name: "description",
      content: "Wweb chat with conversations and details.",
    },
  ];
}

export default function Home() {
  const [conversations, setConversations] =
    useState<Conversation[]>(seedConversations);
  const [selectedId, setSelectedId] = useState<string>(
    seedConversations[0]?.id ?? "",
  );

  const selectedConversation = useMemo(
    () => conversations.find((item) => item.id === selectedId) ?? null,
    [conversations, selectedId],
  );

  const createConversation = () => {
    const stamp = Date.now();
    const nextId = `c-${stamp}`;
    const newConversation: Conversation = {
      id: nextId,
      title: `New conversation ${conversations.length + 1}`,
      lastUpdated: "Just now",
      unread: 0,
      messages: [
        {
          id: `m-${stamp}`,
          sender: "assistant",
          text: "Hi there. What should we explore?",
          time: "Now",
        },
      ],
    };

    setConversations((prev) => [newConversation, ...prev]);
    setSelectedId(nextId);
  };

  return (
    <main className="px-5 pb-16 pt-12 sm:px-10 lg:px-16">
      <section className="mx-auto flex w-full max-w-6xl flex-col gap-7">
        <ChatHeader onCreate={createConversation} />

        <div className="grid gap-5 lg:grid-cols-[minmax(240px,1fr)_minmax(320px,2fr)]">
          <aside className="flex min-h-[520px] flex-col gap-4 rounded-3xl border border-[#79624e1f] bg-white/80 p-6 shadow-[0_18px_40px_rgba(31,24,17,0.12)] backdrop-blur-[16px] lg:min-h-[540px]">
            <ConversationList
              conversations={conversations}
              selectedId={selectedId}
              onSelect={setSelectedId}
            />
          </aside>

          <section className="flex min-h-[520px] flex-col gap-4 rounded-3xl border border-[#79624e1f] bg-white/80 p-6 shadow-[0_18px_40px_rgba(31,24,17,0.12)] backdrop-blur-[16px] lg:min-h-[540px]">
            {selectedConversation ? (
              <ConversationPanel conversation={selectedConversation} />
            ) : (
              <EmptyState onCreate={createConversation} />
            )}
          </section>
        </div>
      </section>
    </main>
  );
}
