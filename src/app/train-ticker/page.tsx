import TrainTicker from '@/components/TrainTicker';

/**
 * Train Ticker Page Component
 * 
 * This is the main page that displays the train station ticker.
 * It provides a full-screen experience similar to real train station displays.
 */
export default function TrainTickerPage() {
  return (
    <div className="min-h-screen bg-gray-900 flex flex-col">
      {/* Header Section */}
      <header className="bg-black text-white py-6">
        <div className="container mx-auto px-4">
          <h1 className="text-4xl md:text-6xl font-bold text-center text-green-400 font-mono">
            🚂 CENTRAL STATION
          </h1>
          <p className="text-center text-gray-300 mt-2 text-lg">
            Live Departure Information
          </p>
        </div>
      </header>

      {/* Main Ticker Display */}
      <main className="flex-1 flex items-center justify-center p-4">
        <div className="w-full max-w-7xl">
          <TrainTicker />
        </div>
      </main>

      {/* Information Section */}
      <section className="bg-gray-800 text-white py-6">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 text-center">
            {/* Real-time Updates */}
            <div className="bg-gray-700 p-4 rounded-lg">
              <h3 className="text-green-400 font-bold text-lg mb-2">
                📡 Real-time Updates
              </h3>
              <p className="text-gray-300 text-sm">
                Information updates every 30 seconds to ensure accuracy
              </p>
            </div>

            {/* Platform Information */}
            <div className="bg-gray-700 p-4 rounded-lg">
              <h3 className="text-blue-400 font-bold text-lg mb-2">
                🚉 Platform Guide
              </h3>
              <p className="text-gray-300 text-sm">
                Platforms A1-A4: East Wing | B1-B3: Central | C1-C3: West Wing
              </p>
            </div>

            {/* Customer Service */}
            <div className="bg-gray-700 p-4 rounded-lg">
              <h3 className="text-yellow-400 font-bold text-lg mb-2">
                ℹ️ Information
              </h3>
              <p className="text-gray-300 text-sm">
                For assistance, visit the information desk or call (555) 123-RAIL
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-black text-gray-400 py-4">
        <div className="container mx-auto px-4 text-center">
          <p className="text-sm">
            Central Station Train Ticker System | 
            <span className="text-green-400 ml-2">System Status: Online</span>
          </p>
          <p className="text-xs mt-1">
            Built with Java Spring Boot + Next.js | Learning Project
          </p>
        </div>
      </footer>
    </div>
  );
}

/**
 * Metadata for the page
 * This helps with SEO and browser tab titles
 */
export const metadata = {
  title: 'Train Ticker - Central Station',
  description: 'Live train departure information for Central Station',
};
