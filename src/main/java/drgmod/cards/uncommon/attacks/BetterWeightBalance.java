package drgmod.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.GainMineralsAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.MineralPower;
import drgmod.powers.ShockPower;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class BetterWeightBalance extends BaseCard {

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 4;
    private static final boolean EXHAUST = true;


    public static final String ID = makeID(BetterWeightBalance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BetterWeightBalance() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(CustomTags.PICKAXE);
        setExhaust(true);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if (this.upgraded){
            setExhaust(false);
        }
    }

    @Override
    public float getTitleFontSize() {
        return 18f;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (p.hasPower(MineralPower.POWER_ID)){
//            addToBot(new ApplyPowerAction(p, p, new MineralPower(p, p.getPower(MineralPower.POWER_ID).amount)));
            addToBot(new GainMineralsAction(p, p.getPower(MineralPower.POWER_ID).amount));
        }
    }
}
