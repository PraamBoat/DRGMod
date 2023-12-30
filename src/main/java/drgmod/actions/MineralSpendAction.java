package drgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import drgmod.powers.IronWillPower;
import drgmod.powers.MineralPower;

public class MineralSpendAction extends AbstractGameAction {

    private int amount;
    private AbstractCreature p;

    public MineralSpendAction(int amount, AbstractCreature p){
        this.amount = amount;
        if (this.amount > p.getPower(MineralPower.POWER_ID).amount){
            this.amount = p.getPower(MineralPower.POWER_ID).amount;
        }
        this.p = p;
    }

    @Override
    public void update() {
        if (!isDone){
            addToBot(new ReducePowerAction(p, p, MineralPower.POWER_ID, amount));
//            if (p.hasPower(IronWillPower.POWER_ID)){
//                addToBot(new GainBlockAction(p, p.getPower(IronWillPower.POWER_ID).amount));
//            }
            isDone = true;
        }
    }
}
