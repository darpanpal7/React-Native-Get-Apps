import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-get-apps' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const GetApps = NativeModules.GetApps
  ? NativeModules.GetApps
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return GetApps.multiply(a, b);
}

export async function getAllApps(): Promise<Array<{ packageName: string }>> {
  if (Platform.OS === 'android') return GetApps.getAllApps();
  return getAppsArray((await GetApps.getApps()) as string);
}

export async function getApps(): Promise<Array<{ packageName: string }>> {
  if (Platform.OS === 'android') return GetApps.getApps();
  return getAppsArray((await GetApps.getApps()) as string).filter(
    (item) => !item.packageName.startsWith('com.apple.')
  );
}

export async function getUpiApps(): Promise<Array<{ packageName: string }>> {
  if (Platform.OS === 'android') return GetApps.getUpiApps();
  return getAppsArray((await GetApps.getApps()) as string).filter(() => false);
}

const getAppsArray = (stream: string | undefined) => {
  if (!stream) return [];
  const regex = /> .* file/;
  stream = stream.slice(1, -1);
  const pList = stream.split(',');

  const resultArray = [];

  for (const item of pList) {
    let packageName = '';
    let idx = item.search(regex);
    if (idx === -1) continue;
    idx = idx + 2;
    while (idx < item.length && item[idx] !== ' ')
      (packageName += item[idx]), (idx += 1);
    resultArray.push({ packageName });
  }
  return resultArray;
};
