import { StyleSheet, Text, View } from "react-native";
export default function AssistantMessage({
  name,
  message,
}: {
  name: string;
  message: string;
}) {
  return (
    <View style={[styles.bubble, styles.assistantBubble]}>
      <Text style={styles.bubbleLabel}>{name}</Text>
      <Text style={styles.bubbleText}>{message}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  bubble: {
    maxWidth: "88%",
    borderRadius: 20,
    paddingHorizontal: 16,
    paddingVertical: 14,
  },
  assistantBubble: {
    backgroundColor: "#FFFFFF",
    alignSelf: "flex-start",
    borderTopLeftRadius: 8,
  },
  bubbleLabel: {
    color: "#0F766E",
    fontSize: 12,
    fontWeight: "700",
    marginBottom: 6,
    textTransform: "uppercase",
    letterSpacing: 0.8,
  },
  bubbleText: {
    color: "#0F172A",
    fontSize: 16,
    lineHeight: 22,
  },
});
