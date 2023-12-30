package drgmod.cards.uncommon.attacks;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PersistFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.BoltsharkAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.MineralPower;
import drgmod.util.CardStats;

public class NBoltsharkX80 extends BaseCard {
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public static final String ID = makeID(NBoltsharkX80.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public NBoltsharkX80() {
        super(ID, info);
        setDamage(0);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        setDamage(magicNumber * AbstractDungeon.player.hand.size());
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new BoltsharkAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn), magicNumber));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public float getTitleFontSize() {
        return 20f;
    }
}
