package com.hcncompany.UltraHardMode;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class main extends JavaPlugin implements Listener {
	
	int time = 1;
	public Inventory inv;
	public Inventory inv2;
	public Inventory inv3;
	public Inventory inv4;
	boolean challenge = false;
	int playersDead = 0;
	int blah = 0;
	
	@Override
	public void onEnable() {
		 getServer().getPluginManager().registerEvents(this, this);
		 this.saveDefaultConfig();
		 createInv();
		 createTimerInv();
		 createConfirmInv();
		 createConfirm2Inv();
		 
		// startup
		// reloads
		// plugin reloads
	}
	
	@Override
	public void onDisable() {
		// shutdown
		// reloads
		// plugin reloads
	}
	

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent ese) {
		if (this.getConfig().getBoolean("general.effects-on-without-challenge") == true || challenge) {
		if (ese.getEntityType() == EntityType.CREEPER) {
			Creeper creeper = (Creeper) ese.getEntity();
			if (this.getConfig().getBoolean("events.charged-creepers") == true) {
			creeper.setPowered(true);
			}
		}
		
		if (ese.getEntityType() == EntityType.SKELETON) {
			Skeleton skeleton = (Skeleton) ese.getEntity();
			if (this.getConfig().getBoolean("events.skeletons-armor") == true) {
			
			skeleton.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			skeleton.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			skeleton.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			skeleton.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			}
			
			if (this.getConfig().getBoolean("events.skeletons-bow") == true) {
				
			int skeletonPower = this.getConfig().getInt("general.skeleton-bow-power");
			ItemStack punchBow = new ItemStack(Material.BOW);
			punchBow.addEnchantment(Enchantment.ARROW_DAMAGE, skeletonPower);
			
			skeleton.getEquipment().setItemInMainHand(punchBow);
			
			}
			
			if (this.getConfig().getBoolean("events.skeletons-riding-horse") == true) {
			
			Location spawnLoc = skeleton.getLocation();
			
			World world = skeleton.getWorld();
			
			SkeletonHorse sk = (SkeletonHorse) world.spawnEntity(spawnLoc, EntityType.SKELETON_HORSE);
			sk.setPassenger(skeleton);
			}
		}
		
		if (ese.getEntityType() == EntityType.SPIDER) {
			if (this.getConfig().getBoolean("events.spider-speed") == true) {
			int spiderSpeed = this.getConfig().getInt("general.spider-speed-amount");
			Spider spider = (Spider) ese.getEntity();
			spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, spiderSpeed));
			}
		}
		
		if(ese.getEntityType() == EntityType.ZOMBIE) {
			
		Zombie zombie = (Zombie) ese.getEntity();
		if (this.getConfig().getBoolean("events.zombies-armor") == true) {
		zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		}
		if (this.getConfig().getBoolean("events.zombies-sword") == true) {
		zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
			}
		}
		
		if(ese.getEntityType() == EntityType.ENDER_DRAGON) {
			EnderDragon ed = (EnderDragon) ese.getEntity();
			if (this.getConfig().getBoolean("events.double-ender-dragon-health") == true) {
			ed.setMaxHealth(400);
			ed.setHealth(400);
			}
		}
		}
		
	}
	

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void join(PlayerJoinEvent pje) {
		if (this.getConfig().getBoolean("general.effects-on-without-challenge") == true || challenge) {
		if (this.getConfig().getBoolean("events.custom-health") == true) {
		Player player = (Player) pje.getPlayer();
		int healthAmount = this.getConfig().getInt("general.custom-health-amount");
		player.setMaxHealth(healthAmount);
		player.setHealth(healthAmount);
		} else {
			Player player = (Player) pje.getPlayer();
			player.setMaxHealth(20);
			player.setHealth(20);
		}
		} else {
			Player player = (Player) pje.getPlayer();
			player.setMaxHealth(20);
			player.setHealth(20);
		}
	}
	
	
	@EventHandler
	public void foodEat(PlayerItemConsumeEvent pice) {
		if (this.getConfig().getBoolean("general.effects-on-without-challenge") == true || challenge) {
		Random rand = new Random();
		Player player = (Player) pice.getPlayer();
		if (this.getConfig().getBoolean("events.poison-from-food") == true) {
		int nyo = rand.nextInt(10);
		nyo += 1;
		if (nyo == 1) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 2));
		}
		}
		}
	}
	
	@EventHandler
	public void bedSleep(PlayerBedEnterEvent pbee) {
		if (this.getConfig().getBoolean("general.effects-on-without-challenge") == true || challenge) {
		Block bed = (Block) pbee.getBed();
		Player player = (Player) pbee.getPlayer();
		Location pl = player.getLocation();
		World pw = player.getWorld();
		if (this.getConfig().getBoolean("events.beds-explode") == true) {
		pw.createExplosion(pl, 10);
		bed.breakNaturally();
		}
		}
	}
	
@SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("challenge")) {
			if (args.length == 1) {
			try {
				Integer.parseInt(args[0]);
			} catch (java.lang.NumberFormatException n) {
				String argue = args[0];
				if (argue.equalsIgnoreCase("beat")) {
					Bukkit.getOnlinePlayers().forEach(p -> {
						int healthAmount = this.getConfig().getInt("general.custom-health-amount");
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1000));
						p.setGameMode(GameMode.SURVIVAL);
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000, 10));
						Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Welcome to Ultra Hard Mode."), 0);
						Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "If you die, you can't respawn. You loose if all players die. You win if you kill the ender dragon."), 60);
						Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Good Luck :)"), 120);
						Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.SLOW), 160);
						Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.BLINDNESS), 160);
						Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Start!"), 160);
						Bukkit.getScheduler ().runTaskLater (this, () -> challenge = true, 160);
						if (this.getConfig().getBoolean("events.custom-health") == true) {
						Bukkit.getScheduler ().runTaskLater (this, () -> p.setMaxHealth(healthAmount), 160);
						Bukkit.getScheduler ().runTaskLater (this, () -> p.setHealth(healthAmount), 160);
						}
						playersDead = 0;
					});
					
				} else {
					sender.sendMessage(ChatColor.RED + ("Invalid Syntax. Try /challenge (Number of days) or /challenge beat."));
				}
				
				return false;
			}
			Bukkit.getOnlinePlayers().forEach(p -> {
				int healthAmount = this.getConfig().getInt("general.custom-health-amount");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1000));
				p.setGameMode(GameMode.SURVIVAL);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000, 10));
				String length = args[0];
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Welcome to Ultra Hard Mode."), 0);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "If you die, you can't respawn. You loose if all players die. You win if you survive " + length + " Minecraft days."), 60);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Good Luck :)"), 120);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.SLOW), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.BLINDNESS), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Start!"), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> challenge = true, 160);
				if (this.getConfig().getBoolean("events.custom-health") == true) {
					Bukkit.getScheduler ().runTaskLater (this, () -> p.setMaxHealth(healthAmount), 160);
					Bukkit.getScheduler ().runTaskLater (this, () -> p.setHealth(healthAmount), 160);
				}
				playersDead = 0;
				if (args.length == 1) {
					time = Integer.parseInt(args[0]);
					}
				blah = Bukkit.getOnlinePlayers().size();
				this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						
						blah = Bukkit.getOnlinePlayers().size();
						if(!(blah == playersDead)) {
							p.sendMessage(ChatColor.GREEN + "You won the challenge! Congrats!");
							challenge = false;
							playersDead = 0;
							p.setGameMode(GameMode.SURVIVAL);
							return;
						}
					}
					},time*20*60*20L);
				});
					
  	
					return false;
		} else if (args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You cannot do this!");
				return true;
			}
			
			Player player = (Player) sender;
			player.openInventory(inv);
			return true;
		}

		return false;
		}
		return false;
		
	}

@SuppressWarnings("deprecation")
@EventHandler
public void onClick(InventoryClickEvent event) {
	

	Player player = (Player) event.getWhoClicked();
	
	if (event.getView().getTitle().equalsIgnoreCase("Set Mode")) {
	if (event.getSlot() == 0) {
		//timer
		event.setCancelled(true);
		player.openInventory(inv2);
	}
	
	if (event.getSlot() == 1) {
		// beat
		event.setCancelled(true);
		player.openInventory(inv4);
		}
	if (event.getSlot() == 8) {
		event.setCancelled(true);
		player.closeInventory();
	}
	
	
	} else if (event.getView().getTitle().equalsIgnoreCase("Amount of Time")) {
		
		if (event.getSlot() == 0) {
			event.setCancelled(true);
			time = 1;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 1) {
			event.setCancelled(true);
			time = 2;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 2) {
			event.setCancelled(true);
			time = 3;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 3) {
			event.setCancelled(true);
			time = 4;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 4) {
			event.setCancelled(true);
			time = 5;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 5) {
			event.setCancelled(true);
			time = 6;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 6) {
			event.setCancelled(true);
			time = 7;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 7) {
			event.setCancelled(true);
			time = 8;
			player.openInventory(inv3);
		}
		if (event.getSlot() == 8) {
			event.setCancelled(true);
			player.closeInventory();
		}
	} else if (event.getView().getTitle().equals("Are you sure?")) {
		if (event.getSlot() == 0) {
			event.setCancelled(true);
			player.closeInventory();
			Bukkit.getOnlinePlayers().forEach(p -> {
				int healthAmount = this.getConfig().getInt("general.custom-health-amount");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1000));
				p.setGameMode(GameMode.SURVIVAL);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000, 10));
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Welcome to Ultra Hard Mode."), 0);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "If you die, you can't respawn. You loose if all players die. You win if you survive " + time + " Minecraft days."), 60);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Good Luck :)"), 120);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.SLOW), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.BLINDNESS), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Start!"), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> challenge = true, 160);
				if (this.getConfig().getBoolean("events.custom-health") == true) {
					Bukkit.getScheduler ().runTaskLater (this, () -> p.setMaxHealth(healthAmount), 160);
					Bukkit.getScheduler ().runTaskLater (this, () -> p.setHealth(healthAmount), 160);
				}
				playersDead = 0;
				blah = Bukkit.getOnlinePlayers().size();
				this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						
						blah = Bukkit.getOnlinePlayers().size();
						if(!(blah == playersDead)) {
							p.sendMessage(ChatColor.GREEN + "You won the challenge! Congrats!");
							challenge = false;
							playersDead = 0;
							p.setGameMode(GameMode.SURVIVAL);
							return;
						}
					}
					},time*20*60*20L);
				});
		}
		if (event.getSlot() == 1) {
			event.setCancelled(true);
		player.closeInventory();
		}
		if (event.getSlot() == 8) {
			event.setCancelled(true);
			player.closeInventory();
		}
	} else if (event.getView().getTitle().equals("Are You Sure?")) {
		if (event.getSlot() == 0) {
			event.setCancelled(true);
			player.closeInventory();
			Bukkit.getOnlinePlayers().forEach(p -> {
				int healthAmount = this.getConfig().getInt("general.custom-health-amount");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1000));
				p.setGameMode(GameMode.SURVIVAL);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10000, 10));
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Welcome to Ultra Hard Mode."), 0);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "If you die, you can't respawn. You loose if all players die. You win if you kill the ender dragon."), 60);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Good Luck :)"), 120);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.SLOW), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.removePotionEffect(PotionEffectType.BLINDNESS), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.sendMessage(ChatColor.GREEN + "Start!"), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> challenge = true, 160);
				if (this.getConfig().getBoolean("events.custom-health") == true) {
				Bukkit.getScheduler ().runTaskLater (this, () -> p.setMaxHealth(healthAmount), 160);
				Bukkit.getScheduler ().runTaskLater (this, () -> p.setHealth(healthAmount), 160);
				}
				playersDead = 0;
			});
		}
		if (event.getSlot() == 1) {
			event.setCancelled(true);
			player.closeInventory();
		}
		if (event.getSlot() == 8) {
			event.setCancelled(true);
			player.closeInventory();
		}
	}
	
	return;
	
} 



public void createInv() {
	
	inv = Bukkit.createInventory(null, 9, "Set Mode");
	
	ItemStack timer = new ItemStack(Material.BLUE_WOOL);
	ItemMeta meta = timer.getItemMeta();
	
	
	// Timer
	meta.setDisplayName(ChatColor.GREEN + "Timer");
	List<String> lore = new ArrayList<String>();
	lore.add(ChatColor.GRAY + "Click to start a challenge with a timer.");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv.setItem(0, timer);
	
	// Beat the Game
	timer.setType(Material.RED_WOOL);
	meta.setDisplayName(ChatColor.RED + "Beat The Game");
	lore.add(ChatColor.GRAY + "Click to start a challenge to beat the game.");
	timer.setItemMeta(meta);
	inv.setItem(1, timer);
	
	//Close
	timer.setType(Material.BARRIER);
	meta.setDisplayName(ChatColor.RED + "Close Menu");
	lore.clear();
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv.setItem(8, timer);
}


public void createTimerInv() {
	
	inv2 = Bukkit.createInventory(null, 9, "Amount of Time");
	ItemStack timer = new ItemStack(Material.BLUE_WOOL);
	ItemMeta meta = timer.getItemMeta();
	
	//1 MC Day
	meta.setDisplayName(ChatColor.GREEN + "One Minecraft Day");
	List<String> lore = new ArrayList<String>();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 1 Minecraft Day");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(0, timer);
	
	// 2 MC Days
	timer.setType(Material.CYAN_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Two Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 2 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(1, timer);
	
	// 3 MC Days
	timer.setType(Material.GREEN_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Three Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 3 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(2, timer);
	
	// 4 MC Days
	timer.setType(Material.PURPLE_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Four Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 4 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(3, timer);
	
	// 5 MC Days
	timer.setType(Material.YELLOW_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Five Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 5 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(4, timer);
	
	// 6 MC Days
	timer.setType(Material.ORANGE_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Six Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 6 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(5, timer);
	
	// 7 MC Days
	timer.setType(Material.RED_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Seven Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 7 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(6, timer);

	// 8 MC Days
	timer.setType(Material.LIME_WOOL);
	meta.setDisplayName(ChatColor.GREEN + "Eight Minecraft Days");
	lore.clear();
	lore.add(ChatColor.GRAY + "Click to start a challenge For 8 Minecraft Days");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(7, timer);
	
	// Close
	timer.setType(Material.BARRIER);
	meta.setDisplayName(ChatColor.RED + "Close Menu");
	lore.clear();
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv2.setItem(8, timer);
}

public void createConfirmInv() {
	inv3 = Bukkit.createInventory(null, 9, "Are you sure?");
	ItemStack timer = new ItemStack(Material.GREEN_WOOL);
	ItemMeta meta = timer.getItemMeta();
	
	// Yes
	meta.setDisplayName(ChatColor.GREEN + "Yes!");
	List<String> lore = new ArrayList<String>();
	lore.add(ChatColor.GRAY + "This will start the challenge for the whole server.");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv3.setItem(0, timer);
	
	//No
	timer.setType(Material.RED_WOOL);
	meta.setDisplayName(ChatColor.RED + "No!");
	lore.clear();
	lore.add(ChatColor.GRAY + "Cancel this function.");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv3.setItem(1, timer);
	
	// Close
	timer.setType(Material.BARRIER);
	meta.setDisplayName(ChatColor.RED + "Close Menu");
	lore.clear();
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv3.setItem(8, timer);
}

public void createConfirm2Inv() {
	inv4 = Bukkit.createInventory(null, 9, "Are You Sure?");
	ItemStack timer = new ItemStack(Material.GREEN_WOOL);
	ItemMeta meta = timer.getItemMeta();
	
	// Yes
	meta.setDisplayName(ChatColor.GREEN + "Yes!");
	List<String> lore = new ArrayList<String>();
	lore.add(ChatColor.GRAY + "This will start the challenge for the whole server.");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv4.setItem(0, timer);
	
	//No
	timer.setType(Material.RED_WOOL);
	meta.setDisplayName(ChatColor.RED + "No!");
	lore.clear();
	lore.add(ChatColor.GRAY + "Cancel this function.");
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv4.setItem(1, timer);
	
	// Close
	timer.setType(Material.BARRIER);
	meta.setDisplayName(ChatColor.RED + "Close Menu");
	lore.clear();
	meta.setLore(lore);
	timer.setItemMeta(meta);
	inv4.setItem(8, timer);
}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void respawnEvent(PlayerDeathEvent pre) {
		if (challenge) {
			Player player = (Player) pre.getEntity();
			player.setGameMode(GameMode.SPECTATOR);
			playersDead = playersDead + 1;
			blah = Bukkit.getOnlinePlayers().size();
			if (blah == playersDead) {
				playersDead = 0;
				Bukkit.getOnlinePlayers().forEach(p -> {
				p.sendMessage(ChatColor.DARK_RED + "You lost the challenge. Do /challenge to start again.");
				challenge = false;
				p.setGameMode(GameMode.SURVIVAL);
				if (this.getConfig().getBoolean("general.effects-on-without-challenge") == false) {
					p.setMaxHealth(20);
				}
				
				});
			}
			
		}
		}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void dragonDeath(EntityDeathEvent ede) {
		if (challenge) {
			EntityType dragon = (EntityType) ede.getEntityType();
				if (dragon == EntityType.ENDER_DRAGON) {
					Bukkit.getOnlinePlayers().forEach(p -> {
						p.sendMessage(ChatColor.GREEN + ("You won the challenge! Congrats!"));
						playersDead = 0;
						challenge = false;
						p.setGameMode(GameMode.SURVIVAL);
						if (this.getConfig().getBoolean("general.effects-on-without-challenge") == false) {
							p.setMaxHealth(20);
						}
					});
				}
		}
	}

	
}
	


	



