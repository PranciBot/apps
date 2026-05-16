import type { Conversation } from "./types";

export const seedConversations: Conversation[] = [
  {
    id: "c-001",
    title: "Project kickoff",
    lastUpdated: "Today, 09:42",
    unread: 2,
    messages: [
      {
        id: "m-001",
        sender: "assistant",
        text: "Morning. Want a quick recap from yesterday?",
        time: "09:31",
      },
      {
        id: "m-002",
        sender: "you",
        text: "Yes, but keep it to the top three priorities.",
        time: "09:32",
      },
      {
        id: "m-003",
        sender: "assistant",
        text: "Priorities: ship the beta build, finalize onboarding copy, and align on the launch checklist.",
        time: "09:34",
      },
    ],
  },
  {
    id: "c-002",
    title: "Design feedback",
    lastUpdated: "Yesterday, 18:05",
    unread: 0,
    messages: [
      {
        id: "m-004",
        sender: "you",
        text: "Can you make the buttons feel more tactile?",
        time: "17:48",
      },
      {
        id: "m-005",
        sender: "assistant",
        text: "Adding layered shadows and a subtle press animation. Want me to mock it?",
        time: "17:49",
      },
      {
        id: "m-006",
        sender: "you",
        text: "Yes, but keep the palette warm and avoid bright purples.",
        time: "18:02",
      },
    ],
  },
  {
    id: "c-003",
    title: "Weekly planning",
    lastUpdated: "Mon, 08:12",
    unread: 1,
    messages: [
      {
        id: "m-007",
        sender: "assistant",
        text: "Here is the draft agenda for the week. Want me to prioritize by impact?",
        time: "08:11",
      },
    ],
  },
  {
    id: "c-004",
    title: "Notes to self",
    lastUpdated: "Sun, 21:34",
    unread: 0,
    messages: [
      {
        id: "m-008",
        sender: "you",
        text: "Capture insights from the user interviews.",
        time: "21:34",
      },
    ],
  },
];
