import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init(){
        AppModuleKt.initializeKoin(platModule: nil)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}