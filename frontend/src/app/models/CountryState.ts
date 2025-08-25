import { Country } from "./Country";

export interface CountryState {
    id?: number;
    code: string;
    stateName: string;
    countryId?: number;
    country?: Country;
    active: string;
  }
  