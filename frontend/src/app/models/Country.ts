import { Currency } from './Currency';

export interface Country {
  id?: number;
  code: string;
  countryName: string;
  currencyId?: number;
  currency?: Currency;
  isoCode: string;
  shortCode: string;
  isdCode: string;
  active: string;   
}
