package drgmod.cards.common.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.ThunderheadAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.BurnPower;
import drgmod.powers.MineralPower;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class ThunderheadHeavyAutocannon extends BaseCard {

    private static final int DAMAGE = 6;
    public static final int dam = 6;

    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 1;

    public static final String ID = makeID(ThunderheadHeavyAutocannon.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ThunderheadHeavyAutocannon() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(MAGIC, MAGIC_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo j = new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL);
        addToBot(new ThunderheadAction(p, damage, this.upgraded));
    }

    @Override
    public float getTitleFontSize() {
        return 21f;
    }
}
