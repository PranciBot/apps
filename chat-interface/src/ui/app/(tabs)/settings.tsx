import { MaterialCommunityIcons } from "@expo/vector-icons";
import { StyleSheet, Text, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

export default function SettingsTab() {
  return (
    <SafeAreaView style={styles.safeArea} edges={["top", "left", "right"]}>
      <View style={styles.container}>
        <Text style={styles.kicker}>Settings</Text>
        <Text style={styles.title}>Preferences.</Text>
        <Text style={styles.body}>
          Tune how the assistant behaves and how the app feels.
        </Text>

        <View style={styles.section}>
          <SettingRow
            icon="bell-outline"
            label="Notifications"
            value="Enabled"
          />
          <SettingRow icon="shield-outline" label="Privacy" value="Balanced" />
          <SettingRow
            icon="theme-light-dark"
            label="Appearance"
            value="System"
          />
          <SettingRow
            icon="account-outline"
            label="Account"
            value="Connected"
          />
        </View>
      </View>
    </SafeAreaView>
  );
}

function SettingRow({
  icon,
  label,
  value,
}: {
  icon: string;
  label: string;
  value: string;
}) {
  return (
    <View style={styles.row}>
      <View style={styles.rowLeft}>
        <View style={styles.iconWrap}>
          <MaterialCommunityIcons
            name={icon as never}
            color="#5B21B6"
            size={20}
          />
        </View>
        <Text style={styles.rowLabel}>{label}</Text>
      </View>
      <Text style={styles.rowValue}>{value}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: "#F5F3FF",
  },
  container: {
    flex: 1,
    backgroundColor: "#F5F3FF",
    paddingHorizontal: 20,
    paddingTop: 20,
  },
  kicker: {
    color: "#5B21B6",
    fontSize: 13,
    fontWeight: "700",
    letterSpacing: 1.2,
    textTransform: "uppercase",
    marginBottom: 8,
  },
  title: {
    color: "#5B21B6",
    fontSize: 32,
    fontWeight: "800",
    marginBottom: 10,
  },
  body: {
    color: "#6B21A8",
    fontSize: 16,
    lineHeight: 23,
    marginBottom: 18,
  },
  section: {
    backgroundColor: "#FFFFFF",
    borderRadius: 24,
    paddingVertical: 8,
    paddingHorizontal: 14,
    borderWidth: 1,
    borderColor: "#E9D5FF",
  },
  row: {
    minHeight: 64,
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    borderBottomWidth: StyleSheet.hairlineWidth,
    borderBottomColor: "#E9D5FF",
  },
  rowLeft: {
    flexDirection: "row",
    alignItems: "center",
    gap: 12,
  },
  iconWrap: {
    width: 38,
    height: 38,
    borderRadius: 12,
    backgroundColor: "#F3E8FF",
    alignItems: "center",
    justifyContent: "center",
  },
  rowLabel: {
    color: "#0F172A",
    fontSize: 16,
    fontWeight: "600",
  },
  rowValue: {
    color: "#6B21A8",
    fontSize: 14,
    fontWeight: "600",
  },
});
