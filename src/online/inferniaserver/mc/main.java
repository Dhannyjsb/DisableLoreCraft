package online.inferniaserver.mc;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener,CommandExecutor{

	  @Override
	  public void onEnable()
	  {

	    getServer().getPluginManager().registerEvents(this, this);
	    saveDefaultConfig();
	    this.getServer().getConsoleSender().sendMessage(getConfig().getString("prefix").replace("&", "§") + " Disable Lore Craft Active");
	  }
	  
	  public void onDisable() {
		  this.saveConfig();
		    this.getServer().getConsoleSender().sendMessage(getConfig().getString("prefix").replace("&", "§") + " Disable Lore Craft Shutdown");
	  }
	  
	    @SuppressWarnings("deprecation")
		@Override
	    public boolean onCommand(CommandSender sender, Command command, String label, String[] arg) {
	        if(command.getName().equalsIgnoreCase("disablelorecraft")){
	            if(sender.hasPermission("disablelorecraft.admin")){
	                if(arg.length < 1){
	                    sender.sendMessage("----------------"+getConfig().getString("prefix").replace("&", "§")+"----------------");   	
	                    sender.sendMessage("§a /disablelorecraft reload" + " §7-§b Reload Plugin");
	                    sender.sendMessage("§a /disablelorecraft status" + " §7-§b Cek Status Item Lore");
	                    sender.sendMessage("§a /disablelorecraft set" + " §7-§b add Lore to Item");
	                    sender.sendMessage("§a /disablelorecraft remove" + " §7-§b remove Lore Status Item");
	                    return true;
	                } if(arg[0].equals("reload")) {
	                      reloadConfig();
	                    sender.sendMessage(getConfig().getString("prefix").replace("&", "§") + " Plugin has been reloaded");
	        		    this.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+ getConfig().getString("prefix").replace("&", "§") + " Plugin has been reloaded");
	                    return true;
	                }
	                if(arg[0].equals("status")) {
	                    sender.sendMessage(getConfig().getString("prefix").replace("&", "§") + " Disable Lore: " + getConfig().getString("lore").replace("&", "§"));
	                    return true;
	                }
	                if(arg[0].equals("set")) {
	                    Player p = (Player)sender;
	                    ItemStack is = p.getItemInHand();
	                    ItemMeta im = is.getItemMeta();
	                    List<String> loreItem1 = new ArrayList<String>();
	                    if (im.hasLore()) {
	                    	loreItem1 = im.getLore();
	                    } 
	                    	loreItem1.add(ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore")));
		                    im.setLore(loreItem1);
		                    p.getItemInHand().setItemMeta(im);
	                    	p.sendMessage(getConfig().getString("prefix").replace("&", "§") + (" ") + getConfig().getString("message.addlore").replace("&", "§"));
		                    return true;
	                }
	                if(arg[0].equals("remove")) {
	                    Player p = (Player)sender;

	                    ItemStack item2 = new ItemStack(p.getItemInHand());
	                    ItemMeta meta = item2.getItemMeta();

	                    List<String> lore = new ArrayList<String>();
	        		    if ((meta.hasLore()) && 
	        				(meta.getLore() != null) && 
	        				(meta.getLore().contains(ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore"))))) {

	                    	String name = p.getItemInHand().getItemMeta().getDisplayName();
	                    	for (String str : p.getItemInHand().getItemMeta().getLore()) {
	                    	lore.add(str);

	                    }
		                    meta.setDisplayName(name);
		                    lore.remove(ChatColor.translateAlternateColorCodes('&', getConfig().getString("lore")));
		                    meta.setLore(lore);
		                    p.getItemInHand().setItemMeta(meta);
	                    	p.sendMessage(getConfig().getString("prefix").replace("&", "§") + (" ") + getConfig().getString("message.removelore").replace("&", "§"));
	                    }
	        		    else{
	                    	p.sendMessage(getConfig().getString("prefix").replace("&", "§") + (" ") + getConfig().getString("message.nolore").replace("&", "§"));
	        		    }
	                }else {
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
		        
		        for (int i = 0; i < 10; i++) {
		          if ((e.getInventory().getItem(i) != null) && 
		            (e.getInventory().getItem(i).hasItemMeta()) && 
		            (e.getInventory().getItem(i).getItemMeta().getLore().contains(getConfig().getString("lore").replace("&", "§")))) {
		            e.getInventory().setResult(air);
		            p.sendMessage(getConfig().getString("prefix").replace("&", "§") + (" ") + getConfig().getString("message.craft").replace("&", "§"));
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
				(event.getItem().getItemMeta().getLore().contains(getConfig().getString("lore").replace("&", "§")))){
		      event.setCancelled(true);
		      p.sendMessage(getConfig().getString("prefix").replace("&", "§")  + (" ") +  getConfig().getString("message.consume").replace("&", "§"));
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
			(event.getItemInHand().getItemMeta().getLore().contains(getConfig().getString("lore").replace("&", "§"))))
		    {
		      event.setCancelled(true);
		      p.sendMessage(getConfig().getString("prefix").replace("&", "§")  + (" ") +  getConfig().getString("message.use").replace("&", "§"));
		    }
		  }
		  
		  @EventHandler
		  public void onProjectileLaunch(PlayerInteractEvent event)
		  {
		    Player p = event.getPlayer();
		    if ((event.getItem() != null) && 
		      (event.getItem().getItemMeta() != null) && 
		      (event.getItem().getItemMeta().getLore() != null) && 
		      (event.getItem().getItemMeta().getLore().contains(getConfig().getString("lore").replace("&", "§"))))
		    {
		      event.setCancelled(true);
		      p.sendMessage(getConfig().getString("prefix").replace("&", "§")  + (" ") +  getConfig().getString("message.use").replace("&", "§"));
		    }
		  }
}
