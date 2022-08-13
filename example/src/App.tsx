import * as React from 'react';

import {
  StyleSheet,
  View,
  Text,
  Button,
  ScrollView,
  SafeAreaView,
} from 'react-native';
import { getApps, getAllApps, getUpiApps } from 'react-native-get-apps';

export default function App() {
  const [array, setArray] = React.useState<Array<{ packageName: string }>>([]);
  const handlePress = async (type: number) => {
    switch (type) {
      case 0:
        setArray(await getAllApps());
        break;
      case 1:
        setArray(await getApps());
        break;
      case 2:
        setArray(await getUpiApps());
        break;
      default:
        setArray([]);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollContainer}>
        <View style={styles.contentContainer}>
          <Button title="All Apps" onPress={() => handlePress(0)} />
          <Button title="Non System Apps" onPress={() => handlePress(1)} />
          <Button title="Upi Supported Apps" onPress={() => handlePress(2)} />
          <Button title="Clear Selection" onPress={() => handlePress(-1)} />
          {array.map((item, idx) => (
            <Text key={idx}>{item.packageName}</Text>
          ))}
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  scrollContainer: {
    width: '100%',
    flexGrow: 1,
  },
  container: {
    flex: 1,
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  contentContainer: {
    flexGrow: 1,
    justifyContent: 'space-between',
    alignItems: 'center',
  },
});
