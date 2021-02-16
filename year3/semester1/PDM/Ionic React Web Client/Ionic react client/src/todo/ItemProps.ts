export interface ItemProps {
  _id?: string;
  title: string;
  putere: string;
  culoare: string;
  createdOn?: string;
  version: number;
  conflict?: boolean;
  location?: {long: number, lat: number};
}

