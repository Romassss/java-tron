package org.tron.stresstest.dispatch.creator.MultiSign;

import com.google.protobuf.ByteString;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Utils;
import org.tron.core.Wallet;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.stresstest.dispatch.AbstractTransactionCreator;
import org.tron.stresstest.dispatch.GoodCaseTransactonCreator;
import org.tron.stresstest.dispatch.TransactionFactory;
import org.tron.stresstest.dispatch.creator.CreatorCounter;

public class MultiSignSendCoin extends AbstractTransactionCreator implements GoodCaseTransactonCreator {

    private String ownerAddress = "TYtZeP1Xnho7LKcgeNsTY2Xg3LTpjfF6G5";
    private String ownerKey = "795D7F7A3120132695DFB8977CC3B7ACC9770C125EB69037F19DCA55B075C4AE";
    private String toAddress = commonToAddress;
    private long amount = 1L;
    private String privateKey = commonWitnessPrivateKey;

    @Override
    protected Protocol.Transaction create() {
        TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
        String[] permissionKeyString = new String[2];

        permissionKeyString[0] = "795D7F7A3120132695DFB8977CC3B7ACC9770C125EB69037F19DCA55B075C4AE";

        permissionKeyString[1] = "76fb5f55710c7ad6a98f73dd38a732f9a69a7b3ce700a694363a50572fa2842a";

        //permissionKeyString[2] = "549c7797b351e48ab1c6bb5857138b418012d97526fc2acba022357d49c93ac0";


        Contract.TransferContract contract = Contract.TransferContract.newBuilder()
                .setOwnerAddress(ByteString.copyFrom(Wallet.decodeFromBase58Check(ownerAddress)))
                .setToAddress(ByteString.copyFrom(Wallet.decodeFromBase58Check(toAddress)))
                .setAmount(amount)
                .build();
        Protocol.Transaction transaction = createTransaction(contract, Protocol.Transaction.Contract.ContractType.TransferContract);

        transaction = Multisign(transaction, permissionKeyString);
        return transaction;
    }
}