import { GenericWrapper } from './GenericWrapper';
import { Acquirer } from './Acquirer';
import { MerchantInstitutionDetail } from './MerchantInstitutionDetail';

export interface MerchantInstitution {
  id?: number;
  institution?: GenericWrapper;
  acquirer?: Acquirer;
  code?: string;
  name: string;
  description: string;
  activateOn: string; 
  expiryOn: string;
  totalMerchant: number;
  totalLocation: number;
  totalDevice: number;
  expried: boolean;
  merchantInstitutionDetail?: MerchantInstitutionDetail;
  message?: string;
  locked: string; 
  deleted: string;
}
