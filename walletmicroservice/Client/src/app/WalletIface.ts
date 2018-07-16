export interface WalletIface {
    id: String;
    name: String;
    currentBalance: number;
    transactionIds: string[];
}
