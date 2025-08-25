import { Country } from "./Country";

export interface Acquirer {
  id?: number;
  name: string;
  code: string;
  description: string;
  countryId: number;
  country?: Country;
  totalMerchantGroup: number;
  onusValidate: string;
  refundOffline: string;
  adviceMatch: boolean;
  posSms: string;
  posDms: string;
  txtnypeSms: string;
  txtnypeDms: string;
  accounttypeSms: string;
  accounttypeDms: string;
  deleted: string;
  active: string;
}
