package cz.coffee.skript.interactiveGui.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.config.validate.SectionValidator;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.function.Function;
import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import cz.coffee.SkGoat;
import cz.coffee.skript.interactiveGui.base.GUISlot;
import cz.coffee.skript.interactiveGui.base.IGUI;
import cz.coffee.skript.interactiveGui.base.enums.SlotTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static cz.coffee.SkGoat.GUI_MAP;
import static cz.coffee.util.SimpleUtils.color;
import static cz.coffee.util.SimpleUtils.hex;

@Name("Interactive GUI")
@Description("You can define custom interactive GUI")
@Examples("custom interactive gui \"test\"")
@Since("1.0")

public class SecCustomGUI extends Section {

    public static boolean INTERACTIVE_GUI_EVENT_LOAD = false;
    private Expression<String> guiName;
    private Expression<Player> player;

    private final HashMap<Integer, SlotTypes> registeredSlots = new HashMap<>();

    private String name;
    private boolean restricsSlots;
    private String invSection;
    private final static SectionValidator sectionStruct = new SectionValidator()
            .addEntry("name", true)
            .addEntry("restricts slots", true)
            .addEntry("inventory", true)
            .addSection("slot settings", true)
            .addSection("trigger", true);

    private final static SectionValidator getSectionStructSetting = new SectionValidator()
            .addEntry("addable slots", true)
            .addEntry("removable slots", true)
            .addEntry("interactive slots", true)
            .addEntry("draggable slots", true)
            .addEntry("clickable slots", true);

    static {
        Skript.registerSection(SecCustomGUI.class, "[register] interactive [g]ui %string% [for %player%]");
    }

    @Override
    protected @Nullable TriggerItem walk(@NotNull Event e) {
        // empty arrays
        final ArrayList<Integer> menuSize = new ArrayList<>();
        final Set<GUISlot> guiSlots = new HashSet<>();
        final UUID uuid = UUID.randomUUID();

        final Player player = this.player.getSingle(e);
        final String invIdName = guiName.getSingle(e);

        Inventory bukkitINV = Bukkit.createInventory(player, InventoryType.CHEST, Component.text(hex(color(name != null ? name : "interactive inventory " + uuid))));

        Inventory invP = null;
        if (Objects.equals(invSection, "new Inventory_0")) {
            invP = bukkitINV;
        } else {
            Object o;
            if (invSection.startsWith("{") && invSection.endsWith("}")) {
                // Case Variable, supported only
                boolean local = invSection.replace("{", "").startsWith("_");
                o = Variables.getVariable(invSection.replaceAll("[{}_]", ""), e, local);
            } else if (invSection.endsWith("()") && Functions.getFunction(invSection.replaceAll("[()]", "")) != null) {
                // Case Function
                Function<?> f = Functions.getFunction(invSection.replaceAll("[()]", ""));
                assert f != null;
                //o = f.execute(new Object[0][0])[0];
                Skript.error("You can't use a function here!");
                return walk(e, false);
            } else {
                o = bukkitINV;
            }

            if (o instanceof Inventory) {
                invP = (Inventory) o;
                bukkitINV = invP;
            } else if (o != null) {
                Skript.error("input isn't a inventory ... '" + o.getClass() + "'");
            }
        }


        for (int i = 0; bukkitINV.getSize() > i; i++) menuSize.add(i);
        assert invP != null;
        if (restricsSlots)
            for (int m : menuSize) guiSlots.add(new GUISlot(m, SlotTypes.NaN));
        else
            for (int m : menuSize) guiSlots.add(new GUISlot(m, null));


        final Set<GUISlot> rs = new HashSet<>(guiSlots);

        if (!registeredSlots.isEmpty()) {
            registeredSlots.forEach((slot, type) -> {
                for (GUISlot gs : guiSlots) {
                    if (gs.getSlotType().equals(SlotTypes.NaN) && gs.getIndex() == slot) {
                        rs.remove(gs);
                        rs.add(new GUISlot(slot, type));
                    }
                }
            });

        }

        Inventory w = null;
        if (name.length() > 0) {
            w = Bukkit.createInventory(
                    bukkitINV.getHolder(),
                    bukkitINV.getSize(),
                    Component.text(hex(color(name)))
            );
            w.setContents(bukkitINV.getContents());
            w.setMaxStackSize(bukkitINV.getMaxStackSize());
            Variables.setVariable(invSection.replaceAll("[{_}]", ""), name.length()>0 ? w : bukkitINV, e, invSection.replace("{", "").startsWith("_"));
        }
        assert w != null;
        GUI_MAP.put("IGUI_" + invIdName, new IGUI(
                name.length()>0 ? w : bukkitINV,
                rs
        ));
        return walk(e, true);
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "interactive gui " + guiName.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult, @NotNull SectionNode sectionNode, @NotNull List<TriggerItem> triggerItems) {
        guiName = (Expression<String>) exprs[0];
        player = (Expression<Player>) exprs[1];
        sectionNode.convertToEntries(0);

        name = sectionNode.get("name", "").replaceAll("\"", "");
        restricsSlots = Boolean.parseBoolean(sectionNode.get("restricts slots", "true"));
        String invString = sectionNode.getValue("inventory");
        invSection = Objects.requireNonNullElse(invString, "new Inventory_0");
        if(sectionNode.get("slot settings") instanceof SectionNode) {
            SectionNode sc = (SectionNode) sectionNode.get("slot settings");
            if (sc != null) {
                sc.convertToEntries(0);
                sc.validate(getSectionStructSetting);


                Node add_N = sc.get("addable slots");
                Node rem_N = sc.get("removable slots");
                Node cli_N = sc.get("clickable slots");
                Node drg_N = sc.get("draggable slots");
                Node int_N = sc.get("interactive slots");


                if (restricsSlots) {
                    for (String w : sc.get("addable slots", "NaN").split(",")) {
                        if (w.trim().matches("[0-9]+")) {
                            registeredSlots.put(Integer.parseInt(w.trim()), SlotTypes.PUT_IN);
                        } else {
                            if (add_N != null) {
                                SkGoat.info(color("&6&lLine: "+add_N.getLine() + "  &7(" + Objects.requireNonNull(add_N.getParent()) + ")"));
                                SkGoat.info(color("     &c"+"custom interactive gui: You can only use numbers here!"));
                            }
                        }
                    }
                    for (String w : sc.get("removable slots", "NaN").split(",")) {
                        if (w.trim().matches("[0-9]+")) {
                            registeredSlots.put(Integer.parseInt(w.trim()), SlotTypes.TAKE_OUT);
                        } else {
                            if (rem_N != null) {
                                SkGoat.info(color("&6&lLine: "+rem_N.getLine() + "  &7(" + Objects.requireNonNull(rem_N.getParent()) + ")"));
                                SkGoat.info(color("     &c"+"custom interactive gui: You can only use numbers here!"));
                            }
                        }
                    }
                    for (String w : sc.get("clickable slots", "NaN").split(",")) {
                        if (w.trim().matches("[0-9]+")) {
                            registeredSlots.put(Integer.parseInt(w.trim()), SlotTypes.CLICKABLE_SLOT);
                        } else {
                            if (cli_N != null) {
                                SkGoat.info(color("&6&lLine: "+cli_N.getLine() + "  &7(" + Objects.requireNonNull(cli_N.getParent()) + ")"));
                                SkGoat.info(color("     &c"+"custom interactive gui: You can only use numbers here!"));
                            }
                        }
                    }
                    for (String w : sc.get("draggable slots", "NaN").split(",")) {
                        if (w.trim().matches("[0-9]+")) {
                            registeredSlots.put(Integer.parseInt(w.trim()), SlotTypes.DRAGGABLE_SLOT);
                        } else {
                            if (drg_N != null) {
                                SkGoat.info(color("&6&lLine: "+drg_N.getLine() + "  &7(" + Objects.requireNonNull(drg_N.getParent()) + ")"));
                                SkGoat.info(color("     &c"+"custom interactive gui: You can only use numbers here!"));
                            }
                        }
                    }
                    for (String w : sc.get("interactive slots", "NaN").split(",")) {
                        if (w.trim().matches("[0-9]+")) {
                            registeredSlots.put(Integer.parseInt(w.trim()), SlotTypes.INTERACTIVE_SLOT);
                        } else {
                            if (int_N != null) {
                                SkGoat.info(color("&6&lLine: "+int_N.getLine() + "  &7(" + Objects.requireNonNull(int_N.getParent()) + ")"));
                                SkGoat.info(color("     &c"+"custom interactive gui: You can only use numbers here!"));
                            }
                        }
                    }
                }
            }
        }

        if(sectionNode.get("trigger") instanceof SectionNode) {
            SectionNode sc2 = (SectionNode) sectionNode.get("trigger");
            assert sc2 != null;
            loadCode(sc2);
        }

        if (sectionStruct.validate(sectionNode)) {
            INTERACTIVE_GUI_EVENT_LOAD = true;
            return true;
        }
        Skript.error("Something went wrong in the 'interactive gui " + guiName.toString() + "'");
        return false;
    }
}
