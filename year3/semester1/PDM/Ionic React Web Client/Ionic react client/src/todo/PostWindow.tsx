import {RouteComponentProps} from "react-router";
import {
    IonButton,
    IonButtons, IonCol,
    IonContent, IonGrid,
    IonHeader, IonInput,
    IonItem,
    IonLabel, IonList,
    IonPage, IonRow,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import React, {useContext, useState} from "react";
import { ItemContext } from './ItemProvider';
import Item from "./Item";
import Asset from "./Asset";
const PostWindow: React.FC<RouteComponentProps> = ({history}) => {
    const { items, saving, savingError, saveItem, postItem, assets } = useContext(ItemContext);
    const [name, setName] = useState('');
    const handleSave = () => {
        let editedItem = {name, postBy: ""}
        postItem && postItem(editedItem)
    }
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle id='anim1'>Post</IonTitle>
                    <IonButtons slot="end">
                        <IonButton onClick={handleSave}>
                            Create
                        </IonButton>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <IonGrid>
                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel id='anim21' position="floating">Name</IonLabel>
                                <IonInput id='anim22' value={name} onIonChange={e => setName(e.detail.value || '')} />
                            </IonItem>
                        </IonCol>
                        <IonCol>
                            {assets && (
                                <IonList>
                                    {assets.map(({id, name, postBy, borrowers, status}) => <IonItem>
                                        <Asset key={id} postBy={postBy} name={name} />
                                    </IonItem>)}
                                </IonList>
                            )}
                        </IonCol>
                    </IonRow>
                </IonGrid>

            </IonContent>
        </IonPage>
    )
}

export default PostWindow;