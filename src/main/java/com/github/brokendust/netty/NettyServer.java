package com.github.brokendust.netty;

import com.github.brokendust.UISystem;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.bukkit.Bukkit;

public class NettyServer {

    public static void init() {
        new Thread(() -> {
            EventLoopGroup bossGroups = new NioEventLoopGroup();
            EventLoopGroup workerGroups = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroups, workerGroups)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childHandler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new ServerHandler());
                            }

                        });
                Bukkit.getConsoleSender().sendMessage("§7[ §fUISystem §7] §cNetty Server §a√");
                ChannelFuture channelFuture = bootstrap.bind(10005).sync();
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                bossGroups.shutdownGracefully();
                workerGroups.shutdownGracefully();
            }
        }).start();
    }

}
