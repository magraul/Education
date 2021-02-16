import React from 'react';
import {IonButton, IonButtons, IonImg, IonItem, IonLabel, IonList, IonRow, IonToolbar} from '@ionic/react';
import { ItemProps } from './ItemProps';
import {AssetProps} from "./AssetProps";

interface ItemPropsExt extends ItemProps {
    onEdit: (_id?: string) => void;
}

const Asset: React.FC<AssetProps> = ({ id, name, postBy, borrowers,status }) => {
    return (
        <IonItem className='card'>
            <IonList>
                <IonLabel className='label'><b><h1>Nume</h1></b> {name}</IonLabel>
                  </IonList>

        </IonItem>
    );
};

export default Asset;
