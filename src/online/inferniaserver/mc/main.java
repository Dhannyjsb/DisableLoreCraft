package online.inferniaserver.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener,CommandExecutor{

      String myLore = getConfig().getString("lore");
      String ColorLore = ChatColor.translateAlternateColorCodes('&', myLore);
      String msg = getConfig().getString("message-craft");
      String MsgColor = ChatColor.translateAlternateColorCodes('&', msg);
      String msgconsume = getConfig().getString("message-consume");
      String msgconsumeColor = ChatColor.translateAlternateColorCodes('&', msgconsume);
      String msgplace = getConfig().getString("message-use");
      String msguseeColor = ChatColor.translateAlternateColorCodes('&', msgplace);
	  @Override
	  public void onEnable()
	  {
	    getServer().getPluginManager().registerEvents(this, this);
	    saveDefaultConfig();
	    System.out.print(ChatColor.AQUA+"Disable Lore Craft Enable");
	  }
	  
	  public void onDisable() {
		  this.saveConfig();
	    System.out.print(ChatColor.DARK_RED+"Disable Lore Craft Disable");
	  }
	  
	    @Override
	    public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
	        if(command.getName().equalsIgnoreCase("disablelorecraft")){
	            if(sender.hasPermission("disablelorecraft.admin")){
	                if(arg.length < 1){
	                    sender.sendMessage("disablelorecraft reload");
	                    return true;
	                } if(arg[0].equals("reload")) {
	                      this.saveDefaultConfig();
	                      this.reloadConfig();
	                    sender.sendMessage(ChatColor.RED + " plugin has been reloaded");
	                    return true;
	                }else {
	                    sender.sendMessage("dont have permission!");
	                    return true;
	                      }
	            }
	        }
	        return true;
	    }
		
		  @EventHandler
		  public void onPlayerCraft(PrepareItemCraftEvent e){

		    ItemStack air = new ItemStack(org.bukkit.Material.AIR);
		    for (org.bukkit.entity.HumanEntity entity : e.getViewers()) {
		      if ((entity instanceof Player)) {
		        Player p = (Player)entity;
		        
		        for (int i = 0; i < 9; i++) {
		          if ((e.getInventory().getItem(i) != null) && 
		            (e.getInventory().getItem(i).hasItemMeta()) && 
		            (e.getInventory().getItem(i).getItemMeta().getLore().contains(ColorLore))) {
		            e.getInventory().setResult(air);
		            p.sendMessage(MsgColor);
		          }
		        }
		      }
		    }
		  }
		  
		  @EventHandler
		  public void onPlayerItemConsume(PlayerItemConsumeEvent event)
		  {
		    Player p = event.getPlayer();
		    if ((event.getItem() != null) && 
				(event.getItem().getItemMeta() != null) && 
				(event.getItem().getItemMeta().getLore() != null) && 
				(event.getItem().getItemMeta().getLore().contains(ColorLore))){
		      event.setCancelled(true);
		      p.sendMessage(msgconsumeColor);
		    }
		  }
		  
		  @EventHandler
		  public void noPlace(BlockPlaceEvent event)
		  {
		    Player p = event.getPlayer();
		    if 
		    ((event.getItemInHand() != null) && 
			(event.getItemInHand().hasItemMeta()) && 
			(event.getItemInHand().getItemMeta().hasLore()) && 
			(event.getItemInHand().getItemMeta().getLore().contains(ColorLore)))
		    {
		      event.setCancelled(true);
		      p.sendMessage(msguseeColor);
		    }
		  }
		  
		  @EventHandler
		  public void onProjectileLaunch(PlayerInteractEvent event)
		  {
		    Player p = event.getPlayer();
		    if ((event.getItem() != null) && 
		      (event.getItem().getItemMeta() != null) && 
		      (event.getItem().getItemMeta().getLore() != null) && 
		      (event.getItem().getItemMeta().getLore().contains(ColorLore)))
		    {
		      event.setCancelled(true);
		      p.sendMessage(msguseeColor);
		    }
		  }
}
