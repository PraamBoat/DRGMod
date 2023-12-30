package drgmod.cards.uncommon.attacks;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.BreachCutterAction;
import drgmod.actions.MineralSpendAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.MineralPower;
import drgmod.util.CardStats;

public class BreachCutter extends BaseCard {

    private static final int DAMAGE = 16;
    private static final int UPG_DAMAGE = 5;

    private static final int MAGIC = 20;
    private static final int UPG_MAGIC = -5;

    public static final String ID = makeID(BreachCutter.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BreachCutter() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
//        setCustomVar("mineralUse", 20, -5);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MineralSpendAction(magicNumber, p));
        addToBot(new BreachCutterAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse){
            return false;
        }
        if (p.hasPower(MineralPower.POWER_ID)){
            if (p.getPower(MineralPower.POWER_ID).amount >= magicNumber){
                return true;
            }
            else{
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                return false;
            }
        }
        else{
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        return false;
    }
}
