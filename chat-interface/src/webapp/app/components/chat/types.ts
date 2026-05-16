export type Message = {
  id: string;
  sender: "assistant" | "you";
  text: string;
  time: string;
};

export type Conversation = {
  id: string;
  title: string;
  lastUpdated: string;
  unread: number;
  messages: Message[];
};
