import { useEffect, useState } from 'react';
import { NetworkStatus, Plugins } from '@capacitor/core';

const { Network } = Plugins;

const initialState = {
  connected: false,
  connectionType: 'unknown',
}

export const useNetwork = () => {
  const [networkStatus, setNetworkStatus] = useState(initialState)
  let connectedN = false
  useEffect(() => {
    const handler = Network.addListener('networkStatusChange', handleNetworkStatusChange);
    Network.getStatus().then(handleNetworkStatusChange);
    let canceled = false;
    return () => {
      canceled = true;
      handler.remove();
    }

    function handleNetworkStatusChange(status: NetworkStatus) {
      console.log('useNetwork - status change', status);
      if (!canceled) {
        setNetworkStatus(status);
        connectedN = status.connected
      }
    }
  }, [])
  return { networkStatus, connectedN };
};
