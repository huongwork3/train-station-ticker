'use client';

import React, { useEffect, useState } from 'react';

/**
 * Interface defining the structure of train schedule data
 * This matches the TrainScheduleDTO from our Java backend
 */
interface TrainSchedule {
  trainNumber: string;
  trainName: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  platform: string;
  status: string;
  delayMinutes: number;
  route: string;
}

/**
 * TrainTicker Component
 * 
 * This component displays a scrolling ticker of train departures
 * similar to what you'd see at a real train station.
 * 
 * Features:
 * - Fetches data from Java backend API
 * - Auto-refreshes every 30 seconds
 * - Smooth scrolling animation
 * - Color-coded status indicators
 * - Error handling and loading states
 */
export default function TrainTicker() {
  // State management using React hooks
  const [trains, setTrains] = useState<TrainSchedule[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [lastUpdated, setLastUpdated] = useState<Date | null>(null);

  /**
   * Function to fetch train data from the backend API
   */
  const fetchTrains = async () => {
    try {
      const apiUrl = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';
      const response = await fetch(`${apiUrl}/trains`);
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      
      const data = await response.json();
      setTrains(data);
      setLastUpdated(new Date());
      setError(''); // Clear any previous errors
      
    } catch (err) {
      console.error('Error fetching train data:', err);
      setError(err instanceof Error ? err.message : 'Failed to fetch train data');
    } finally {
      setLoading(false);
    }
  };

  /**
   * useEffect hook to handle component lifecycle
   * - Fetches data when component mounts
   * - Sets up auto-refresh interval
   * - Cleans up interval when component unmounts
   */
  useEffect(() => {
    // Initial data fetch
    fetchTrains();
    
    // Set up auto-refresh every 30 seconds
    const refreshInterval = parseInt(process.env.NEXT_PUBLIC_REFRESH_INTERVAL || '30000');
    const interval = setInterval(fetchTrains, refreshInterval);
    
    // Cleanup function - runs when component unmounts
    return () => clearInterval(interval);
  }, []);

  /**
   * Function to get appropriate CSS classes for status styling
   */
  const getStatusColor = (status: string) => {
    switch (status) {
      case 'ON_TIME':
        return 'text-green-400';
      case 'DELAYED':
        return 'text-red-400';
      case 'CANCELLED':
        return 'text-gray-400';
      default:
        return 'text-white';
    }
  };

  /**
   * Function to format status text for display
   */
  const formatStatus = (status: string, delayMinutes: number) => {
    if (status === 'DELAYED' && delayMinutes > 0) {
      return `DELAYED (+${delayMinutes}min)`;
    }
    return status.replace('_', ' ');
  };

  // Loading state
  if (loading) {
    return (
      <div className="bg-black text-green-400 font-mono text-lg p-4">
        <div className="text-center">
          <div className="animate-pulse">Loading train schedules...</div>
        </div>
      </div>
    );
  }

  // Error state
  if (error) {
    return (
      <div className="bg-black text-red-400 font-mono text-lg p-4">
        <div className="text-center">
          <div>⚠️ Error: {error}</div>
          <button 
            onClick={fetchTrains}
            className="mt-2 px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition-colors"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  // No trains available
  if (trains.length === 0) {
    return (
      <div className="bg-black text-yellow-400 font-mono text-lg p-4">
        <div className="text-center">No train schedules available</div>
      </div>
    );
  }

  return (
    <div className="bg-black text-green-400 font-mono overflow-hidden border-2 border-green-400">
      {/* Header */}
      <div className="bg-green-400 text-black px-4 py-2 text-center font-bold">
        🚂 LIVE DEPARTURES - CENTRAL STATION
        {lastUpdated && (
          <span className="ml-4 text-sm">
            Last updated: {lastUpdated.toLocaleTimeString()}
          </span>
        )}
      </div>
      
      {/* Ticker Container */}
      <div className="ticker-container h-16 flex items-center relative">
        <div className="ticker-content animate-scroll whitespace-nowrap flex">
          {/* Duplicate the trains array to create seamless loop */}
          {[...trains, ...trains].map((train, index) => (
            <div key={`${train.trainNumber}-${index}`} className="inline-flex items-center mr-16 text-lg">
              {/* Train Number */}
              <span className="text-yellow-400 font-bold mr-2">
                {train.trainNumber}
              </span>
              
              {/* Train Name */}
              <span className="text-white mr-2">
                {train.trainName}
              </span>
              
              {/* Arrow */}
              <span className="text-blue-400 mr-2">→</span>
              
              {/* Destination */}
              <span className="text-blue-400 mr-4">
                {train.destination}
              </span>
              
              {/* Departure Time */}
              <span className="text-green-300 mr-1">Dep:</span>
              <span className="text-green-300 mr-4">
                {train.departureTime}
              </span>
              
              {/* Platform */}
              <span className="text-orange-400 mr-1">Platform:</span>
              <span className="text-orange-400 mr-4">
                {train.platform}
              </span>
              
              {/* Status */}
              <span className={`${getStatusColor(train.status)} font-semibold`}>
                {formatStatus(train.status, train.delayMinutes)}
              </span>
              
              {/* Separator */}
              <span className="text-gray-500 ml-4">|</span>
            </div>
          ))}
        </div>
      </div>
      
      {/* Footer with legend */}
      <div className="bg-gray-900 text-gray-400 px-4 py-1 text-sm text-center">
        <span className="text-green-400">●</span> On Time | 
        <span className="text-red-400"> ●</span> Delayed | 
        <span className="text-gray-400"> ●</span> Cancelled
      </div>
      
      {/* CSS Styles */}
      <style jsx>{`
        .ticker-container {
          position: relative;
          width: 100%;
          background: linear-gradient(90deg, 
            rgba(0,0,0,1) 0%, 
            rgba(0,0,0,0) 5%, 
            rgba(0,0,0,0) 95%, 
            rgba(0,0,0,1) 100%
          );
        }
        
        .ticker-content {
          animation: scroll 120s linear infinite;
        }
        
        @keyframes scroll {
          0% {
            transform: translateX(100%);
          }
          100% {
            transform: translateX(-50%);
          }
        }
        
        /* Pause animation on hover for better readability */
        .ticker-container:hover .ticker-content {
          animation-play-state: paused;
        }
        
        /* Responsive text size */
        @media (max-width: 768px) {
          .ticker-content {
            font-size: 0.875rem;
          }
        }
      `}</style>
    </div>
  );
}
