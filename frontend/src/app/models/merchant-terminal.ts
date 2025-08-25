export interface MerchantTerminal {
  id?: number;
  name: string;
  deviceType: string;
  deviceModelType: string;
  merchantChain: string;
  merchantStore: string;
  terminalId: string;
  pedId: string;
  pedSerialNo: string;
  activateOn: string;
  posSafety: boolean;
  status: boolean;
}