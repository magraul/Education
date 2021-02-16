import React, { useContext, useEffect, useState } from 'react';
import {
  IonButton,
  IonButtons,
  IonContent,
  IonHeader,
  IonInput,
  IonLoading,createAnimation,
  IonPage,
  IonTitle,
  IonToolbar, IonItem, IonLabel, IonFab, IonFabButton, IonIcon, IonGrid, IonRow, IonCol, IonImg, IonActionSheet
} from '@ionic/react';
import { getLogger } from '../core';
import { ItemContext } from './ItemProvider';
import { RouteComponentProps } from 'react-router';
import { ItemProps } from './ItemProps';
import {camera, trash} from "ionicons/icons";
import {useMyLocation} from "./useMyLocation";
import {MyMap} from "./Map";

//import {Photo, usePhotoGallery} from './usePhotoGallery';

import {base64FromPath} from "@ionic/react-hooks/filesystem";
import {useCamera} from "@ionic/react-hooks/camera";
import {CameraResultType, CameraSource} from "@capacitor/core";

const log = getLogger('ItemEdit');
interface ItemEditProps extends RouteComponentProps<{
  id?: string;
}> {}

const ItemEdit: React.FC<ItemEditProps> = ({ history, match }) => {
  const { items, saving, savingError, saveItem } = useContext(ItemContext);
  const [title, setTitle] = useState('');
  const [culoare, setCuloare] = useState('');
  const [putere, setPutere] = useState('');
  const [item, setItem] = useState<ItemProps>();
  const [version, setVersion] = useState(0);
  const {getPhoto} = useCamera();
  const [photo, setPhoto] = useState<any>();
  const [deleteDialog, setDeleteDialog] = useState(false);
  const myLocation = useMyLocation();
  const [location, setLocation] = useState<any>();
  const [myMapLoc, setMyMapLoc] = useState({lat: 0, long: 0});

  useEffect(() => {
    const {latitude: lat, longitude: lng} = myLocation.position?.coords || {}

    if (lat && lng) {
      setMyMapLoc({lat: lat, long: lng});
    }
  }, [myLocation]);

  useEffect(() => {
    log('useEffect');
    const routeId = match.params.id || '';
    const item = items?.find(it => it._id === routeId);
    setItem(item);
    if (item) {
      setTitle(item.title);
      setCuloare(item.culoare)
      setPutere(item.putere)
      setVersion(item.version + 1)
      setLocation(item.location);
      // if (item.version) {
      //
      //   setVersion(item.version + 1)
      // }
    }
  }, [match.params.id, items]);
  const handleSave = () => {
    // if (version)
    //   setVersion(version+1)
    console.log("in handle save")
    console.log(version)
    let editedItem = item ? { ...item, title, putere, culoare, version } : { title, putere, culoare, version };

    if (location)
      editedItem = {...editedItem, location};
    else
      delete editedItem['location'];


    saveItem && saveItem(editedItem).then(() => history.goBack());
  };

  const takePhoto = async () => {
    const cameraPhoto = await getPhoto({
      resultType: CameraResultType.Uri,
      source: CameraSource.Camera,
      quality: 100,
      width: 50,
      height: 50
    });
    const base64Data = await base64FromPath(cameraPhoto.webPath!);

    setPhoto({webviewPath: base64Data});
  };

  const setMapPosition = (e: any) => {
    setLocation({lat: e.latLng.lat(), long: e.latLng.lng()});
  }
  const enterAnimation = (baseEl: any) => {
    const backdropAnimation = createAnimation()
        .addElement(baseEl)
        .fromTo('opacity', 0.1, 1);

    return createAnimation()
        .addElement(baseEl)
        .duration(500)
        .addAnimation([backdropAnimation]);
  }

  const leaveAnimation = (baseEl: any) => {
    return enterAnimation(baseEl).direction('reverse');
  }


  useEffect(simpleAnimation, []);
  useEffect(groupAnimations, []);
  useEffect(chainAnimations, []);
  log('render');
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle id='anim1'>Edit</IonTitle>
          <IonButtons slot="end">
            <IonButton onClick={handleSave}>
              Save
            </IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        {/*<IonItem>*/}
        {/*  <IonLabel position="floating">Id</IonLabel>*/}
        {/*  <IonInput disabled value={item?._id}/>*/}
        {/*</IonItem>*/}
        <IonItem>
          <IonLabel id='anim21' position="floating">Titlu</IonLabel>
          <IonInput id='anim22' value={title} onIonChange={e => setTitle(e.detail.value || '')} />
        </IonItem>
        <IonItem id='anim31'>
          <IonLabel position="floating">Putere</IonLabel>
          <IonInput value={putere} onIonChange={e => setPutere(e.detail.value || '')}/>
        </IonItem>
        <IonItem id='anim32'>
          <IonLabel position="floating">Culoare</IonLabel>
          <IonInput value={culoare} onIonChange={e => setCuloare(e.detail.value || '')}/>
        </IonItem>
        <IonItem id='anim33'>
          <IonLabel position="floating">Data creare</IonLabel>
          <IonInput value={item?.createdOn} readonly/>
        </IonItem>
        <IonInput id = 'anim34' value={title} onIonChange={e => setTitle(e.detail.value || '')} />
        <IonLoading isOpen={saving} />
        {savingError && (
          <div>{savingError.message || 'Failed to save item'}</div>
        )}
        <IonItem onClick={() => photo && setDeleteDialog(true)}>
          <IonLabel>Imagine: </IonLabel>
          {(photo && <img src={photo.webviewPath} alt='' width='300px'/>)}
        </IonItem>
        <IonItem>
          <IonGrid>
            <IonRow>
              <IonLabel>Locatie: {(location && location.long + " / " + location.lat)}</IonLabel>
            </IonRow>
            <IonRow>
              <MyMap
                  lat={location?.lat}
                  lng={location?.long}
                  myLat={myMapLoc.lat}
                  myLng={myMapLoc.long}
                  onMapClick={setMapPosition}
              />
            </IonRow>
          </IonGrid>
        </IonItem>
        <IonFab vertical="bottom" horizontal="center" slot="fixed">
          <IonFabButton onClick={() => takePhoto()}>
            <IonIcon icon={camera}/>
          </IonFabButton>
        </IonFab>
        {/*<IonActionSheet leaveAnimation={leaveAnimation}*/}
        {/*                isOpen={deleteDialog}*/}
        {/*                buttons={[{*/}
        {/*                  text: 'Delete',*/}
        {/*                  role: 'destructive',*/}
        {/*                  icon: trash,*/}
        {/*                  handler: () => {*/}
        {/*                    setPhoto(null);*/}
        {/*                  }*/}
        {/*                }, {*/}
        {/*                  text: 'Cancel',*/}
        {/*                  icon: close,*/}
        {/*                  role: 'cancel'*/}
        {/*                }]}*/}
        {/*                onDidDismiss={() => setDeleteDialog(false)}*/}
        {/*/>*/}
      </IonContent>
    </IonPage>
  );
};


function simpleAnimation() {
  const el = document.querySelector('#anim1');
  if (el) {
    const animation = createAnimation()
        .addElement(el)
        .duration(1000)
        .direction('alternate')
        .iterations(Infinity)
        .keyframes([
          {transform: 'scale(1)', opacity: '1'},
          {transform: 'scale(0.5)', opacity: '0.5'}
        ]);
    animation.play();
  }
}

function groupAnimations() {
  const elB = document.querySelector('#anim21');
  const elC = document.querySelector('#anim22');
  if (elB && elC) {
    const animationA = createAnimation()
        .addElement(elB)
        .direction('alternate')
        .iterations(2)
        .fromTo("transform", "translateX(0px)", "translateX(50px)")
    const animationB = createAnimation()
        .addElement(elC)
        .direction('alternate')
        .iterations(2)
        .fromTo("transform", "translateX(0px)", "translateX(50px)")
    const parentAnimation = createAnimation()
        .duration(500)
        .addAnimation([animationA, animationB]);
    parentAnimation.play();
  }
}

function chainAnimations() {
  const elA = document.querySelector('#anim31');
  const elB = document.querySelector('#anim32');
  const elC = document.querySelector('#anim33');
  const elD = document.querySelector('#anim34');
  if (elA && elB && elC && elD) {
    const animationA = createAnimation()
        .addElement(elA)
        .duration(500)
        .fromTo('opacity', 0.1, 1);
    const animationB = createAnimation()
        .addElement(elB)
        .duration(500)
        .fromTo('opacity', 0.1, 1);
    const animationC = createAnimation()
        .addElement(elC)
        .duration(500)
        .fromTo('opacity', 0.1, 1);
    const animationD = createAnimation()
        .addElement(elD)
        .duration(500)
        .fromTo('opacity', 0.1, 1);
    (async () => {
      await animationA.play();
      await animationB.play();
      await animationC.play();
      await animationD.play();
    })();
  }
}

export default ItemEdit;
