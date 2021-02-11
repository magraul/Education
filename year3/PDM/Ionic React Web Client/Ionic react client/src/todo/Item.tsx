import React from 'react';
import {IonButton, IonButtons, IonImg, IonItem, IonLabel, IonList, IonRow, IonToolbar} from '@ionic/react';
import { ItemProps } from './ItemProps';

interface ItemPropsExt extends ItemProps {
  onEdit: (_id?: string) => void;
}

const Item: React.FC<ItemPropsExt> = ({ _id, title, putere, culoare,createdOn, onEdit }) => {
  return (
    <IonItem className='card' onClick={() => onEdit(_id)}>
        <IonList>
            <IonLabel className='label'><b><h1>Nume</h1></b> {title}</IonLabel>
            <IonLabel className='label'><b><h1>Putere</h1></b> {putere}</IonLabel>
            <IonLabel className='label'><b><h1>Culoare</h1></b> {culoare}</IonLabel>
            <IonLabel className='label'><b><h1>Data adaugarii</h1></b> {createdOn}</IonLabel>
        </IonList>

    </IonItem>
  );
};

export default Item;
