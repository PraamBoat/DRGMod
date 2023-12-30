package drgmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

import static drgmod.MinerMain.makeID;

public class IntoxicationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Intoxication");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean freeCard;
    private boolean canDupe;
    private boolean duping;

    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    // test when change from 3 -> 4, no free card
    public IntoxicationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = false;
        freeCard = true;
        canDupe = true;
        duping = false;
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
    }

    public void atStartOfTurn(){
        freeCard = true;
        canDupe = true;
        duping = false;
        addToBot(new DrawCardAction(this.owner, 1));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 5){
            canDupe = true;
            duping = false;
        }
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        if (!this.owner.hasPower(DrunkFormPower.POWER_ID)){
            addToBot(new LoseBlockAction(this.owner, this.owner, 2));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (this.amount > 1){
            return type == DamageInfo.DamageType.NORMAL ? damage + 2 : damage;
        }
        return damage;
    }

    public float modifyBlock(float blockAmount) {
        if (this.amount > 1 && !this.owner.hasPower(DrunkFormPower.POWER_ID)){
            return blockAmount - 2;
        }
        return blockAmount;
    }

    public void updateDescription(){
        if (owner != null){
            String temp = "";
            if (this.amount > 5){
                this.amount = 5;
            }
            for (int i = 0; i < this.amount; i++){
                temp += DESCRIPTIONS[i];
            }
            this.description = temp;
        }
        else{
            this.description = DESCRIPTIONS[5];
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (this.amount > 2 && type == DamageInfo.DamageType.NORMAL && !this.owner.hasPower(DrunkFormPower.POWER_ID)) {
            return damage * 1.5F;
        }
        return damage;
    }

    public void onCardDraw(AbstractCard card){
        if (this.amount > 2 && freeCard){
            card.setCostForTurn(0);
            freeCard = false;
        }
        else if (this.amount > 3 && card.cost >= 0) {
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            if (card.cost != newCost) {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
            card.freeToPlayOnce = false;
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (this.amount > 4){
            addToBot(new ApplyPowerAction(owner, owner, new EndTurnDeathPower(owner)));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (this.amount > 3 && action.target != this.owner){
            action.target = AbstractDungeon.getMonsters().getRandomMonster();
        }
        if (this.amount > 4){
            if (canDupe && !duping){
                AbstractMonster m = null;
                if (!card.purgeOnUse) {
                    duping = true;
                    canDupe = false;
                    flash();
                    AbstractCard tmp = card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = card.current_x;
                    tmp.current_y = card.current_y;
                    tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = Settings.HEIGHT / 2.0F;
                    if (m != null)
                        tmp.calculateCardDamage(m);
                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, (AbstractMonster) action.target, card.energyOnUse, true, true), true);
                }
            }
            else if (duping){
                duping = false;
            }
            else{
                canDupe = true;
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new IntoxicationPower(owner, amount);
    }
}
