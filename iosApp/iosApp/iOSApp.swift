import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        FirebaseInitializerKt.doInit()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
