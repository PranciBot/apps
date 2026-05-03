import { MaterialCommunityIcons } from "@expo/vector-icons";
import { StyleSheet, Text, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

export default function DiscoverTab() {
  return (
    <SafeAreaView style={styles.safeArea} edges={["top", "left", "right"]}>
      <View style={styles.container}>
        <Text style={styles.kicker}>Discover</Text>
        <Text style={styles.title}>Shortcuts for the day.</Text>
        <Text style={styles.body}>
          Quick actions and starter tiles for common assistant tasks.
        </Text>

        <View style={styles.grid}>
          <View style={[styles.card, styles.cardWarm]}>
            <MaterialCommunityIcons
              name="calendar-check-outline"
              color="#9A3412"
              size={22}
            />
            <Text style={styles.cardTitle}>Plan today</Text>
          </View>
          <View style={[styles.card, styles.cardTeal]}>
            <MaterialCommunityIcons
              name="clipboard-text-outline"
              color="#0F766E"
              size={22}
            />
            <Text style={styles.cardTitle}>Draft notes</Text>
          </View>
          <View style={[styles.card, styles.cardBlue]}>
            <MaterialCommunityIcons name="brain" color="#1D4ED8" size={22} />
            <Text style={styles.cardTitle}>Brainstorm</Text>
          </View>
          <View style={[styles.card, styles.cardSlate]}>
            <MaterialCommunityIcons
              name="star-four-points"
              color="#334155"
              size={22}
            />
            <Text style={styles.cardTitle}>Clean up</Text>
          </View>
        </View>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: "#FFF7ED",
  },
  container: {
    flex: 1,
    backgroundColor: "#FFF7ED",
    paddingHorizontal: 20,
    paddingTop: 20,
  },
  kicker: {
    color: "#9A3412",
    fontSize: 13,
    fontWeight: "700",
    letterSpacing: 1.2,
    textTransform: "uppercase",
    marginBottom: 8,
  },
  title: {
    color: "#9A3412",
    fontSize: 32,
    fontWeight: "800",
    marginBottom: 10,
  },
  body: {
    color: "#7C2D12",
    fontSize: 16,
    lineHeight: 23,
    marginBottom: 18,
  },
  grid: {
    flexDirection: "row",
    flexWrap: "wrap",
    gap: 12,
  },
  card: {
    width: "48%",
    minHeight: 120,
    borderRadius: 22,
    padding: 16,
    justifyContent: "space-between",
    borderWidth: 1,
  },
  cardWarm: {
    backgroundColor: "#FFEDD5",
    borderColor: "#FDBA74",
  },
  cardTeal: {
    backgroundColor: "#CCFBF1",
    borderColor: "#5EEAD4",
  },
  cardBlue: {
    backgroundColor: "#DBEAFE",
    borderColor: "#93C5FD",
  },
  cardSlate: {
    backgroundColor: "#E2E8F0",
    borderColor: "#CBD5E1",
  },
  cardTitle: {
    color: "#0F172A",
    fontSize: 16,
    fontWeight: "700",
    lineHeight: 20,
  },
});
