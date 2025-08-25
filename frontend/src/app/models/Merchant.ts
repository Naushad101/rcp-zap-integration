import { AtmOption } from "./AtmOption";
import { GenericWrapper } from "./GenericWrapper";
import { MerchantDetail } from "./MerchantDetail";
import { MerchantProfile } from "./MerchantProfile";

export interface Merchant {
    id?: number;
    merchantInstitution?: GenericWrapper;
    code?: string;
    name?: string;
    description?: string;
    activateOn?: string;
    expiryOn?: string;
    totalLocation?: number;
    totalDevice?: number;
    locked?: string;
    posSafetyFlag?: string;
    expired?: boolean;
    message?: string;
    reversalTimeout?: string;
    deleted?: string;
    merchantProfile?: MerchantProfile;
    atmOption?: AtmOption;
    acquirerId?: number;
    merchantDetail?: MerchantDetail;
    currency?: GenericWrapper;
    bankName?: string;
    accountNumber?: string;
}