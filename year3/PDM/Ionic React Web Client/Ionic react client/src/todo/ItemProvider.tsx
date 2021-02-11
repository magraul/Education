import React, {useCallback, useContext, useEffect, useReducer} from 'react';
import PropTypes from 'prop-types';
import {getLogger} from '../core';
import {ItemProps} from './ItemProps';
import {createItem, deleteItemA, getColors, getItemsAll, newWebSocket, updateItem, postItemApi, getAssets, getActiveAssets} from './itemApi';
import {AuthContext} from '../auth';
//import {ApplicationContext} from "./ApplicationProvider";
import {Plugins} from '@capacitor/core';
import {useNetwork} from './useNetwork';
import {AssetProps} from "./AssetProps";

const { Network } = Plugins;
const {Storage} = Plugins;

const log = getLogger('ItemProvider');

type SaveItemFn = (item: ItemProps) => Promise<any>;
type PostItemFn = (item: AssetProps) => Promise<any>;
type DeleteItemFn = (item: ItemProps) => Promise<any>;
type GetItemsFn = (culoare: string | undefined, reset: boolean) => Promise<any>;
type BorrowItemFn = (id?: string) => Promise<any>;

export interface ItemsState {
    activeAssets?:AssetProps[],
    assets?: AssetProps[],
    items?: ItemProps[],
    fetching: boolean,
    fetchingError?: Error | null,
    saving: boolean,
    savingError?: Error | null,
    saveItem?: SaveItemFn,
    deleting: boolean,
    deletingError?: Error | null,
    deleteItem?: DeleteItemFn,
    getItemsC?: GetItemsFn,
    disableInfiniteScroll?: boolean,
    colors?: string[],
    postItem?: PostItemFn,
    borrowItem?: BorrowItemFn,

}

interface ActionProps {
    type: string,
    payload?: any,
}

const initialState: ItemsState = {
    fetching: false,
    saving: false,
    deleting: false,
    disableInfiniteScroll: false,
};

const FETCH_ITEMS_STARTED = 'FETCH_ITEMS_STARTED';
const FETCH_ITEMS_SUCCEEDED = 'FETCH_ITEMS_SUCCEEDED';
const FETCH_ITEMS_FAILED = 'FETCH_ITEMS_FAILED';
const SAVE_ITEM_STARTED = 'SAVE_ITEM_STARTED';
const SAVE_ITEM_SUCCEEDED = 'SAVE_ITEM_SUCCEEDED';
const SAVE_ITEM_FAILED = 'SAVE_ITEM_FAILED';
const DELETE_ITEM_STARTED = 'DELETE_ITEM_STARTED'
const DELETE_ITEM_SUCCEDED = 'DELETE_ITEM_SUCCEDED'
const DELETE_ITEM_FAILED = 'DELETE_ITEM_FAILED'
const UPDATE_ITEM_OFFLINE = 'UPDATE_ITEM_OFFLINE'
const SAVE_TRACTOR_OFFLINE = 'SAVE_TRACTOR_OFFLINE'
const DELETE_TRACTOR_OFFLINE = 'DELETE_TRACTOR_OFFLINE'
const SYNC_STATE_STORAGE = 'SYNC_STATE_STORAGE'
const RESET_ITEMS_LIST = 'RESET_ITEMS_LIST'
const COLORS_RECEIVED = 'COLORS_RECEIVED'
const ELIMINA_DIN_STATE = 'ELIMINA_DIN_STATE'
const CONFLICT_APARUT = 'CONFLICT_APARUT'
const REFRESH_ASSETS = 'REFRESH_ASSETS'
const REFRESH_ASSETS_ACTIVE = 'REFRESH_ASSETS_ACTIVE'

const reducer: (state: ItemsState, action: ActionProps) => ItemsState =
    (state, {type, payload}) => {
        switch (type) {
            case FETCH_ITEMS_STARTED:
                return {...state, fetching: true, fetchingError: null};
            case FETCH_ITEMS_SUCCEEDED:
                console.log("in fetch succeded")
                console.log(payload.items)
                console.log(payload.colors)
                if (!state.items)
                    return {...state, items: payload.items, fetching: false, connected: true};
                return {...state, items: [...(state.items || []), ...(payload.items || [])], fetching: false, connected: true, disableInfiniteScroll: payload.items.length < 2};
            case FETCH_ITEMS_FAILED:
                return {...state, fetchingError: payload.error, fetching: false};
            case SAVE_ITEM_STARTED:
                return {...state, savingError: null, saving: true};
            case DELETE_ITEM_STARTED:
                return {...state, deletingError: null, deleting: true};
            case SAVE_ITEM_SUCCEEDED:
                const items = [...(state.items || [])];
                const item = payload.item;
                const index = items.findIndex(it => it._id === item._id);
                if (index === -1) {
                    items.splice(0, 0, item);
                } else {
                    items[index] = item;
                }
                return {...state, items, saving: false};
            case DELETE_ITEM_SUCCEDED:
                const for_delete = payload.item
                const items2 = [...(state.items || [])];
                let index3 = items2?.findIndex(c => c._id == for_delete._id)
                if (index3 == 0) {
                    items2.shift()
                } else if (index3 > 0) {
                    items2?.splice(index3, 1);
                }
                console.log(state)
                console.log(items2)
                return {...state, items: items2, deleting: false}
            case SAVE_ITEM_FAILED:
                return {...state, savingError: payload.error, saving: false};
            case DELETE_ITEM_FAILED:
                return {...state, deletingError: payload.error, deleting: false}
            case UPDATE_ITEM_OFFLINE:
                (async () => {
                    let itemss
                    const res = await Storage.get({key: 'modificate_offline'});
                    if (res.value) {
                        itemss = JSON.parse(res.value).modificate_offline
                        itemss.push(item3)
                    }
                    await Storage.set({
                        key: 'modificate_offline',
                        value: JSON.stringify({
                            modificate_offline: itemss,
                        })
                    });
                })()
                const items3 = [...(state.items || [])]
                console.log("update ofline")
                console.log(items3)
                const item3 = payload.item
                const index4 = items3.findIndex(it => it._id === item3._id)
                console.log(index4)
                items3[index4] = item3
                return {...state, items: items3, saving: false, savingError: Error()}
            case SAVE_TRACTOR_OFFLINE:
                (async () => {
                    let itemss_add
                    const res = await Storage.get({key: 'adaugate_offline'});
                    if (res.value) {
                        itemss_add = JSON.parse(res.value).adaugate_offline
                        itemss_add.push(item5)
                    }
                    console.log(itemss_add)
                    await Storage.set({
                        key: 'adaugate_offline',
                        value: JSON.stringify({
                            adaugate_offline: itemss_add,
                        })
                    });
                })()

                console.log("save offline")
                const items5 = [...(state.items) || []]
                const item5 = payload.item
                item5._id = items5.length.toString()
                items5.splice(0, 0, item5);

                return {...state, items: items5, saving: false, savingError: Error()}
            case DELETE_TRACTOR_OFFLINE:
                const for_delete2 = payload.item
                const items22 = [...(state.items || [])];
                let index32 = items22?.findIndex(c => c._id == for_delete2._id)
                if (index32 == 0) {
                    items22.shift()
                } else if (index32 > 0) {
                    items22?.splice(index32, 1);
                }

                return {...state, items: items22, deleting: false}
            case SYNC_STATE_STORAGE:
            (async () => {
                await Storage.remove({key: 'tractoare'})
                await Storage.set({
                    key: 'tractoare',
                    value: JSON.stringify({
                        tractoare: state.items,
                    })
                });
            })()
                return state;
            case RESET_ITEMS_LIST:

                return {...state, items: []}
            case COLORS_RECEIVED:

                return {...state, colors: payload.colors.culori}
            case ELIMINA_DIN_STATE:
                const for_delete_items = payload.items
                const items_all = [...(state.items || [])];
                console.log("for delete")
                console.log(for_delete_items)
                for(const t in for_delete_items) {
                    let index3 = items_all?.findIndex(c => c._id == for_delete_items[t]._id)
                    if (index3 == 0) {
                        items_all.shift()
                    } else if (index3 > 0) {
                        items_all?.splice(index3, 1);
                    }
                }
                return {...state, items: items_all, deleting: false}
            case CONFLICT_APARUT:
                const item_conflict = payload.item;
                const items_akk = [...(state.items || [])];
                const index22 = items_akk.findIndex(it => it._id === item_conflict._id);
                items_akk[index22].conflict = true
                return {...state, items: items_akk, saving: false};
            case REFRESH_ASSETS:
                const assets = payload.assets;
                return {...state, assets}
            case REFRESH_ASSETS_ACTIVE:
                const activeAssets = payload.activeAssets;
                return {...state, activeAssets}
            default:
                return state;
        }
    };

export const ItemContext = React.createContext<ItemsState>(initialState);

interface ItemProviderProps {
    children: PropTypes.ReactNodeLike,
}

export const ItemProvider: React.FC<ItemProviderProps> = ({children}) => {
    const {token} = useContext(AuthContext);
    const [state, dispatch] = useReducer(reducer, initialState);
    const {items, fetching, fetchingError, saving, savingError, deleting, deletingError, disableInfiniteScroll, colors, assets, activeAssets} = state;
    //useEffect(getItemsEffect, [token]);
    useEffect(wsEffect, [token]);
    const { networkStatus, connectedN } = useNetwork();
    useEffect(() => {
        if (networkStatus.connected) {
            console.log("BACK ONLINE")
            sendOfflineItemsEffect()
        }
    }, [networkStatus])
   // useEffect(getCollorsEffect, [token])
    useEffect(getAssetsEffect, []);
    //const [pageCnt, setPageCnt] = useState<number>(0)
    var pageCnt = 0;


    const saveItem = useCallback<SaveItemFn>(saveItemCallback, [token]);
    const postItem = useCallback<PostItemFn>(postItemCallback, []);
    const deleteItem = useCallback<DeleteItemFn>(deleteItemCallback, [token]);
    const getItemsC = useCallback<GetItemsFn>(getItemsCallback, [token]);
    const borrowItem = useCallback<BorrowItemFn>(borrowCallback, []);
    const value = {items, fetching, fetchingError, saving, savingError,postItem, saveItem, deleting, deletingError, deleteItem, getItemsC, disableInfiniteScroll, colors, assets, activeAssets, borrowItem};
    log('returns');
    return (
        <ItemContext.Provider value={value}>
            {children}
        </ItemContext.Provider>
    );

    function getAssetsEffect() {
        getAssetsA();

        async function getAssetsA() {
            const res = await Storage.get({key: 'username'});
            let username = ''
            if (res.value) {
                username = JSON.parse(res.value).username
            }
            const assets = await getAssets(username);
            const activeAssets = await getActiveAssets();
            dispatch({ type: REFRESH_ASSETS, payload: { assets: assets } });
            dispatch({type: REFRESH_ASSETS_ACTIVE, payload:{activeAssets: activeAssets}});
        }
    }

    function getCollorsEffect() {
        getCollors();

        async function getCollors() {
            const colors = await getColors(token);
            dispatch({type: COLORS_RECEIVED, payload: {colors}});
        }

    }



    function sendOfflineItemsEffect() {
        sendItems();

        async function sendItems() {
            const res = await Storage.get({key: 'adaugate_offline'});
            if (res.value) {
                let adaugate_offline = JSON.parse(res.value).adaugate_offline
                dispatch(({type: ELIMINA_DIN_STATE, payload: {items: adaugate_offline}}))
                for(const i in adaugate_offline) {
                    delete adaugate_offline[i]._id
                    createItem(token, adaugate_offline[i])
                }
                await Storage.remove({key: 'adaugate_offline'})
            }
            const res2 = await Storage.get({key: 'modificate_offline'});
            if (res2.value) {
                let modificate_offline = JSON.parse(res2.value).modificate_offline
                for (const i in modificate_offline) {
                    updateItem(token, modificate_offline[i]).then(r=> {
                        if (r.conflict){
                            dispatch({type: CONFLICT_APARUT, payload: {item:r}})
                        }
                    })
                }
                await Storage.remove({key: 'modificate_offline'})
            }
        }
    }

    function getItemsEffect() {
        let canceled = false;
        console.log("se face")
        fetchItems();
        return () => {
            canceled = true;
        }

        async function fetchItems() {
            if (!token?.trim()) {
                return;
            }
            try {
                log('fetchItems started');
                dispatch({type: FETCH_ITEMS_STARTED});
                const items2 = await getItemsAll(token);
                log('fetchItems succeeded');
                if (!canceled) {
                    await Storage.set({
                        key: 'tractoare',
                        value: JSON.stringify({
                            tractoare: items2,
                        })
                    });
                    let items = items2.slice(0, 2)
                    dispatch({type: FETCH_ITEMS_SUCCEEDED, payload: {items}});
                    ++pageCnt

                    const colors = await getColors(token);
                    dispatch({type: COLORS_RECEIVED, payload: {colors}});
                }

            } catch (error) {
                const res = await Storage.get({key: 'tractoare'});
                if (res.value) {
                    let items = JSON.parse(res.value).tractoare
                    console.log(items)
                    dispatch({type: FETCH_ITEMS_SUCCEEDED, payload: {items}});
                } else {

                }


            }
        }
    }

    async function getItemsCallback(culoare: string | undefined, reset: boolean) {
        if (!culoare && reset)
        return
            try {
            log('fetchItems started');
            dispatch({type: FETCH_ITEMS_STARTED});
            if (reset){
                pageCnt = 0
                dispatch({type: RESET_ITEMS_LIST})
            }

            console.log(pageCnt)

            const res = await Storage.get({key: 'tractoare'});
            if (res.value) {
                let st_items = JSON.parse(res.value).tractoare
                let items
                if (culoare) {
                    items = st_items.filter((t: ItemProps) => t.culoare === culoare).slice(pageCnt*2, pageCnt*2+2)
                    //items = getItems(token, pageCnt, 2, culoare)
                } else {
                    items = st_items.slice(pageCnt*2, pageCnt*2+2)
                    //items = getItems(token, pageCnt, 2, undefined)
                }
                dispatch({type: FETCH_ITEMS_SUCCEEDED, payload: {items}});
                ++pageCnt;
            } else {
                await Storage.set({
                        key: 'tractoare',
                        value: JSON.stringify({
                            tractoare: items,
                        })
                    });
            }



        } catch (error) {
            const res = await Storage.get({key: 'tractoare'});
            if (res.value) {
                let items = JSON.parse(res.value).tractoare
                console.log(items)
                dispatch({type: FETCH_ITEMS_SUCCEEDED, payload: {items}});
            } else {

            }


        }
    }

    async function deleteItemCallback(item: ItemProps) {
        try {
            const res = await Storage.get({key: 'offline'});
            if (res.value) {
                console.log("offline e")
                console.log(items)
                dispatch({type: DELETE_TRACTOR_OFFLINE, payload: {item}})
                //dispatch({type: SYNC_STATE_STORAGE})

            } else {

                dispatch({type: DELETE_ITEM_STARTED});
                await (deleteItemA(token, item));
                log('saveItem succeeded');
                //dispatch({ type: DELETE_ITEM_SUCCEDED, payload: { item: item } });
            }
        } catch (error) {
            log('saveItem failed');
            dispatch({type: DELETE_ITEM_FAILED, payload: {error}});
        }
    }

    async function borrowCallback(id?:string) {
        const res = await Storage.get({key: 'username'});
        let username = ''
        if (res.value) {
            username = JSON.parse(res.value).username
        }
        const index = activeAssets?.findIndex(a => a.id == id);

        if(activeAssets && index && username){
            let arr:any =  activeAssets[index].borrowers

            arr?.push(username)
        }
        // let arr =
        // await borrowItemApi(id)

    }
    async function postItemCallback(item: AssetProps){
        const res = await Storage.get({key: 'username'});
        let username = ''
        if (res.value) {
            item.postBy = JSON.parse(res.value).username
            username = JSON.parse(res.value).username
        }
        try {
            await postItemApi(item);
            const assets = await getAssets(username);
            dispatch({ type: REFRESH_ASSETS, payload: { assets: assets } });
        }catch (error) {
            alert("ceva nu a mers")
        }




    }

    async function saveItemCallback(item: ItemProps) {
        try {
            log('saveItem started');
            dispatch({type: SAVE_ITEM_STARTED});
            console.log("salvare sau update")
            console.log(item)
            const savedItem = await (item._id ? updateItem(token, item) : createItem(token, item));

            console.log("A TRECUT")
            log('saveItem succeeded');
            dispatch({ type: SAVE_ITEM_SUCCEEDED, payload: { item: savedItem } });

            const res = await Storage.get({key: 'tractoare'});
            if (res.value) {
                let items = JSON.parse(res.value).tractoare
                const index = items.findIndex((t: ItemProps) => t._id ===savedItem._id);
                if (index === -1) {
                    items.splice(0, 0, savedItem);
                } else {
                    items[index] = savedItem;
                }
                await Storage.set({
                    key: 'tractoare',
                    value: JSON.stringify({
                        tractoare: items.map((i: { photo: any; })=>delete i.photo),
                    })
                });
            }
        } catch (error) {
            console.log("OFFLINE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            dispatch({type: SAVE_ITEM_STARTED});
            if (item._id){
                //modificare offline
                console.log("modificare offline")
                const res = await Storage.get({key: 'modificate_offline'});
                if (res.value){

                    dispatch(({type: UPDATE_ITEM_OFFLINE, payload: { item: item }}))
                } else {
                    await Storage.set({
                        key: 'modificate_offline',
                        value: JSON.stringify({
                            modificate_offline: [],
                        })
                    });

                    dispatch(({type: UPDATE_ITEM_OFFLINE, payload: { item: item }}))
                }

            } else {
                //salvare offline
                const res = await Storage.get({key: 'adaugate_offline'});
                if (res.value){
                    dispatch({type: SAVE_TRACTOR_OFFLINE, payload: {item: item}})
                } else {
                    await Storage.set({
                        key: 'adaugate_offline',
                        value: JSON.stringify({
                            adaugate_offline: [],
                        })
                    });
                    dispatch({type: SAVE_TRACTOR_OFFLINE, payload: {item: item}})
                }
            }
        }

        let status
        Network.getStatus().then(R=> status = R.connected)
        if (status){



        } else {

        }

        // try {
        //     const res = await Storage.get({key: 'offline'});
        //     if (res.value) {
        //         item._id ? dispatch({type: UPDATE_ITEM_OFFLINE, payload: {item}}) :
        //             dispatch({type: SAVE_TRACTOR_OFFLINE, payload: {item}})
        //         dispatch({type: SYNC_STATE_STORAGE})
        //     } else {
        //         log('saveItem started');
        //         dispatch({type: SAVE_ITEM_STARTED});
        //         const savedItem = await (item._id ? updateItem(token, item) : createItem(token, item));
        //         log('saveItem succeeded');
        //         //dispatch({ type: SAVE_ITEM_SUCCEEDED, payload: { item: savedItem } });
        //     }
        // } catch (error) {
        //     log('saveItem failed');
        //
        //     dispatch({type: SAVE_ITEM_FAILED, payload: {error}});
        //
        //
        // }
    }

    function wsEffect() {
        let canceled = false;
        log('wsEffect - connecting');
        let closeWebSocket: () => void;
        if (token?.trim()) {
            closeWebSocket = newWebSocket(token, message => {
                console.log('am primit')
                console.log(message.payload)
                if (canceled) {
                    console.log("a luat foc serverul")
                    return;
                }
                Network.getStatus().then(R=> {
                    if (R.connected) {
                        const {type, payload: item} = message;
                        log(`ws message, item ${type}`);
                        if (type === 'created' || type === 'updated') {
                            dispatch({type: SAVE_ITEM_SUCCEEDED, payload: {item}});
                        } else if (type === 'deleted') {
                            dispatch({type: DELETE_ITEM_SUCCEDED, payload: {item: item}});
                        } else if (type === 'conflict') {
                            dispatch({type: CONFLICT_APARUT, payload: {item}})
                        }
                    } else {
                        console.log("NU SUNTEM DAMN CONECTATI")
                    }
                })

            });
        }
        return () => {
            log('wsEffect - disconnecting');
            canceled = true;
            closeWebSocket?.();
        }
    }
};
