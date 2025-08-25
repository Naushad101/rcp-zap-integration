import { CountryState } from "./CountryState";
import { CountryWrapper } from "./CountryWrapper";

export interface MerchantDetail {
    id?: number;
    address1: string;
    address2?: string;
    city?: string;
    zip?: string;
    country?: CountryWrapper;
    countryState?: CountryState;
    phone?: string;
    fax?: string;
    email: string;
}