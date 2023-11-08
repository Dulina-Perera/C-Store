'use client';
import GlobalState from '@/context';
import './globals.css';
import { Inter } from 'next/font/google';
import Navbar from '@/components/Navbar';
import Menu from '@/components/Menu';


const inter = Inter({ subsets: ['latin'] });

export default function RootLayout({ children }) {
  return (
    <html lang="en" className='bg-black'>
      <body className={inter.className}>
        <GlobalState>
          <div className='mt-20'>
            <Navbar />
            <Menu className = "top-0"/>
            
            <main>
              {children}
            </main>
          </div>
        </GlobalState>
      </body>
    </html>
  );
}