#import "GetApps.h"

#ifdef RCT_NEW_ARCH_ENABLED
#import "RNGetAppsSpec.h"
#endif

// #include <objc/runtime.h>

@implementation GetApps
RCT_EXPORT_MODULE()


RCT_REMAP_METHOD(getApps,
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
  // Class LSApplicationWorkspace_class = objc_getClass("LSApplicationWorkspace");
  // SEL selector=NSSelectorFromString(@"defaultWorkspace");
  // NSObject* workspace = [LSApplicationWorkspace_class performSelector:selector];

  // SEL selectorALL = NSSelectorFromString(@"allApplications");

  NSString *result = nil;
  // NSLog(@"apps: %@", [workspace performSelector:selectorALL]);
  resolve(result);
}


// Example method
// See // https://reactnative.dev/docs/native-modules-ios
RCT_REMAP_METHOD(multiply,
                 multiplyWithA:(double)a withB:(double)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
  NSNumber *result = @(a * b);

  resolve(result);
}

// Don't compile this code when we build for the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeGetAppsSpecJSI>(params);
}
#endif

@end
