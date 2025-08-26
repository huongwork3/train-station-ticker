import Link from 'next/link';

/**
 * Home Page Component
 * 
 * This is the landing page that welcomes users and provides navigation
 * to the train ticker system.
 */
export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-900 via-purple-900 to-gray-900 flex items-center justify-center">
      <div className="text-center text-white max-w-4xl mx-auto px-4">
        {/* Main Title */}
        <h1 className="text-6xl md:text-8xl font-bold mb-6 text-transparent bg-clip-text bg-gradient-to-r from-green-400 to-blue-500">
          🚂 CENTRAL STATION
        </h1>
        
        {/* Subtitle */}
        <p className="text-xl md:text-2xl mb-8 text-gray-300">
          Welcome to Central Station Train Information System
        </p>
        
        {/* Description */}
        <div className="bg-black/30 backdrop-blur-sm rounded-lg p-6 mb-8 border border-gray-700">
          <p className="text-lg text-gray-200 mb-4">
            Real-time train departure and arrival information powered by modern technology
          </p>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm text-gray-300">
            <div>
              <span className="text-green-400">✓</span> Live Updates
            </div>
            <div>
              <span className="text-blue-400">✓</span> Platform Information
            </div>
            <div>
              <span className="text-yellow-400">✓</span> Delay Notifications
            </div>
          </div>
        </div>
        
        {/* Navigation Buttons */}
        <div className="space-y-4 md:space-y-0 md:space-x-4 md:flex md:justify-center">
          <Link 
            href="/train-ticker" 
            className="block md:inline-block bg-green-600 hover:bg-green-700 px-8 py-4 rounded-lg text-lg font-semibold transition-all duration-300 transform hover:scale-105 shadow-lg"
          >
            🚂 View Live Departures
          </Link>
          
          <Link 
            href="/train-ticker" 
            className="block md:inline-block bg-blue-600 hover:bg-blue-700 px-8 py-4 rounded-lg text-lg font-semibold transition-all duration-300 transform hover:scale-105 shadow-lg"
          >
            📊 Station Information
          </Link>
        </div>
        
        {/* Features Section */}
        <div className="mt-12 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          <div className="bg-gray-800/50 p-4 rounded-lg border border-gray-700">
            <div className="text-2xl mb-2">⏰</div>
            <h3 className="font-semibold text-green-400">Real-Time</h3>
            <p className="text-sm text-gray-300">Live departure times</p>
          </div>
          
          <div className="bg-gray-800/50 p-4 rounded-lg border border-gray-700">
            <div className="text-2xl mb-2">🚉</div>
            <h3 className="font-semibold text-blue-400">Multi-Platform</h3>
            <p className="text-sm text-gray-300">All platforms covered</p>
          </div>
          
          <div className="bg-gray-800/50 p-4 rounded-lg border border-gray-700">
            <div className="text-2xl mb-2">📱</div>
            <h3 className="font-semibold text-yellow-400">Mobile Ready</h3>
            <p className="text-sm text-gray-300">Works on all devices</p>
          </div>
          
          <div className="bg-gray-800/50 p-4 rounded-lg border border-gray-700">
            <div className="text-2xl mb-2">🔄</div>
            <h3 className="font-semibold text-purple-400">Auto-Refresh</h3>
            <p className="text-sm text-gray-300">Always up to date</p>
          </div>
        </div>
        
        {/* Technology Stack Info */}
        <div className="mt-12 text-sm text-gray-400">
          <p className="mb-2">Built with modern technology stack:</p>
          <div className="flex flex-wrap justify-center gap-4">
            <span className="bg-orange-600/20 text-orange-400 px-3 py-1 rounded">Java Spring Boot</span>
            <span className="bg-blue-600/20 text-blue-400 px-3 py-1 rounded">MySQL Database</span>
            <span className="bg-black/40 text-white px-3 py-1 rounded">Next.js</span>
            <span className="bg-cyan-600/20 text-cyan-400 px-3 py-1 rounded">Tailwind CSS</span>
          </div>
        </div>
      </div>
    </div>
  );
}
