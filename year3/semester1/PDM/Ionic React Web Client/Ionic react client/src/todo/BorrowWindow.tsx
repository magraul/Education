import {RouteComponentProps} from "react-router";
import {IonButton, IonCol, IonItem, IonList, IonPage} from "@ionic/react";
import React, {useContext} from "react";
import {ItemContext} from "./ItemProvider";
import Asset from "./Asset";

const BorrowWindow: React.FC<RouteComponentProps> = ({history}) => {
    const { activeAssets, borrowItem} = useContext(ItemContext);
    const borrowObj = (id?: string) => {
        borrowItem && borrowItem(id);
    }
    return (
        <IonPage>
            {activeAssets && (
                <IonList>
                    {activeAssets.map(({id, name, postBy, borrowers, status}) => <IonItem>
                        <Asset key={id} postBy={postBy} name={name} />
                        <IonButton color = "danger" slot="end" onClick={() => borrowObj(id)}>Borrow </IonButton>
                        <IonButton color = "success" slot="end" >Return </IonButton>
                    </IonItem>)}
                </IonList>
            )}
        </IonPage>
    )
}

export default BorrowWindow;