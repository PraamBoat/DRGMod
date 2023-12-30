package drgmod.cards.common.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import drgmod.actions.MineralSpendAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.MineralPower;
import drgmod.util.CardStats;

public class WarthogAuto210 extends BaseCard {

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public static final String ID = makeID(WarthogAuto210.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public WarthogAuto210() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public float getTitleFontSize() {
        return 21f;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MineralSpendAction(5, p));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber)));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse){
            return false;
        }
        if (p.hasPower(MineralPower.POWER_ID)){
            if (p.getPower(MineralPower.POWER_ID).amount >= 5){
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
