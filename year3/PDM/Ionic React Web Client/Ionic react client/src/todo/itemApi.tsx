import axios from 'axios';
import { authConfig, baseUrl, getLogger, withLogs } from '../core';
import { ItemProps } from './ItemProps';
import {Plugins} from "@capacitor/core";
import {AssetProps} from "./AssetProps";
const itemUrl = `http://${baseUrl}`;


export const getItems: (token: string, pageCnt: number, numItems: number, culoare: string | undefined) => Promise<ItemProps[]> = (token, pageCnt, numItems, culoare) => {
  if (culoare){
    return withLogs(axios.get(`${itemUrl}/filter/${culoare}`, authConfig(token, pageCnt, numItems)), 'get items with filter')
  }
  return withLogs(axios.get(`${itemUrl}/page`, authConfig(token, pageCnt, numItems)), 'getItems');
}

export const getItemsAll: (token: string) => Promise<ItemProps[]> = (token) => {

  return withLogs(axios.get(itemUrl, authConfig(token)), 'getItems');
}

export const getColors: (token: string) => Promise<string[]> = (token) => {
  return withLogs(axios.get(`${itemUrl}/culori`, authConfig(token, 0, 0)), 'getColors');
}
export const createItem: (token: string, item: ItemProps) => Promise<ItemProps> = (token, item) => {
  return withLogs(axios.post(itemUrl, item, authConfig(token)), 'createItem');
}

export const postItemApi: (item: AssetProps) =>Promise<AssetProps> = (item) => {
  return withLogs(axios.post(itemUrl + "/asset", item, {}), 'postAsset');
}
export const updateItem: (token: string, item: ItemProps) => Promise<ItemProps> = (token, item) => {
  return withLogs(axios.put(`${itemUrl}/${item._id}`, item, authConfig(token)), 'updateItem');
}

export const getAssets: (username: string) => Promise<AssetProps[]> = (username) => {
  return withLogs(axios.get(itemUrl + "/asset?postBy=" + username, {}), "getAssets");
}

// export const borrowItemApi:

export const getActiveAssets: () => Promise<AssetProps[]> = () => {
  return withLogs(axios.get(itemUrl + "/asset?status=active", {}), "actives");
}
export const deleteItemA: (token: string, item: ItemProps) => Promise<ItemProps[]> = (token, item) => {
  return withLogs(axios.delete(`${itemUrl}/${item._id}`, authConfig(token)), 'deleteItem');
}

interface MessageData {
  type: string;
  payload: ItemProps;
}

const log = getLogger('ws');

export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
  const ws = new WebSocket(`ws://${baseUrl}`);
  ws.onopen = () => {
    log('web socket onopen');
    (async () => {
      const { Storage } = Plugins;
      await Storage.remove({ key: 'offline' });
    })();

    ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
  };
  ws.onclose = () => {
    log('web socket onclose');
    (async () => {
      const { Storage } = Plugins;
      await Storage.set({
        key: 'offline',
        value: '1'});
    })();
      console.log("a luat foc serverul")

  };
  ws.onerror = error => {
    log('web socket onerror', error);
  };
  ws.onmessage = messageEvent => {
    log('web socket onmessage');
    onMessage(JSON.parse(messageEvent.data));
  };
  return () => {
    ws.close();
  }
}
