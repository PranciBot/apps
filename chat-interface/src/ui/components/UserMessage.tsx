import { StyleSheet, Text, View } from "react-native";
export default function UserMessage({
  name,
  message,
}: {
  name: string;
  message: string;
}) {
  return (
    <View style={[styles.bubble, styles.userBubble]}>
      <Text style={styles.bubbleLabelUser}>{name}</Text>
      <Text style={styles.bubbleTextUser}>{message}</Text>
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
  userBubble: {
    backgroundColor: "#0F766E",
    alignSelf: "flex-end",
    borderTopRightRadius: 8,
  },
  bubbleLabelUser: {
    color: "#A7F3D0",
    fontSize: 12,
    fontWeight: "700",
    marginBottom: 6,
    textTransform: "uppercase",
    letterSpacing: 0.8,
  },
  bubbleTextUser: {
    color: "#FFFFFF",
    fontSize: 16,
    lineHeight: 22,
  },
});
