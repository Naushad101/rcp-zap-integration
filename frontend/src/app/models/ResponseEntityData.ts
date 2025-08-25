import { MerchantStore } from "./merchant-store";

export interface ResponseEntityData<T = any> {
  success: boolean;
  status: string;
  message: string;
  data: T | null;
}